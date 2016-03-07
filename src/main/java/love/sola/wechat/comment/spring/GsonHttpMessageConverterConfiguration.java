package love.sola.wechat.comment.spring;

import com.google.gson.*;
import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import love.sola.wechat.comment.entity.Comment;
import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

import java.io.IOException;
import java.util.Date;

@Configuration
@ConditionalOnClass(Gson.class)
@ConditionalOnMissingClass(name = "com.fasterxml.jackson.core.JsonGenerator")
public class GsonHttpMessageConverterConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public GsonHttpMessageConverter gsonHttpMessageConverter(Gson gson) {
		GsonHttpMessageConverter converter = new GsonHttpMessageConverter();
		converter.setGson(gson);
		return converter;
	}

	@Bean
	public Gson gson() {
		return new GsonBuilder()
				.addSerializationExclusionStrategy(new ExclusionStrategy() {
					@Override
					public boolean shouldSkipField(FieldAttributes fieldAttributes) {
						final Expose expose = fieldAttributes.getAnnotation(Expose.class);
						return expose != null && !expose.serialize();
					}

					@Override
					public boolean shouldSkipClass(Class<?> aClass) {
						return false;
					}
				})
				.addDeserializationExclusionStrategy(new ExclusionStrategy() {
					@Override
					public boolean shouldSkipField(FieldAttributes fieldAttributes) {
						final Expose expose = fieldAttributes.getAnnotation(Expose.class);
						return expose != null && !expose.deserialize();
					}

					@Override
					public boolean shouldSkipClass(Class<?> aClass) {
						return false;
					}
				})
				.registerTypeAdapter(Date.class, (JsonDeserializer<Date>) (json, typeOfT, context) -> new Date(json.getAsJsonPrimitive().getAsLong()))
				.registerTypeAdapter(Date.class, (JsonSerializer<Date>) (src, typeOfSrc, context) -> new JsonPrimitive(src.getTime()))
				.registerTypeAdapter(Comment.class, (JsonSerializer<Comment>) (src, typeOfSrc, context) -> context.serialize(new Comment.Json(src)))
				.registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY)
				.create();
	}

	public static class HibernateProxyTypeAdapter extends TypeAdapter<HibernateProxy> {

		public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() {
			@Override
			@SuppressWarnings("unchecked")
			public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
				return (HibernateProxy.class.isAssignableFrom(type.getRawType()) ? (TypeAdapter<T>) new HibernateProxyTypeAdapter(gson) : null);
			}
		};
		private final Gson context;

		private HibernateProxyTypeAdapter(Gson context) {
			this.context = context;
		}

		@Override
		public HibernateProxy read(JsonReader in) throws IOException {
			throw new UnsupportedOperationException("Not supported");
		}

		@SuppressWarnings({"rawtypes", "unchecked"})
		@Override
		public void write(JsonWriter out, HibernateProxy value) throws IOException {
			if (value == null) {
				out.nullValue();
				return;
			}
			// Retrieve the original (not proxy) class
			Class<?> baseType = Hibernate.getClass(value);
			// Get the TypeAdapter of the original class, to delegate the serialization
			TypeAdapter delegate = context.getAdapter(TypeToken.get(baseType));
			// Get a filled instance of the original class
			Object unproxiedValue = ((HibernateProxy) value).getHibernateLazyInitializer()
					.getImplementation();
			// Serialize the value
			delegate.write(out, unproxiedValue);
		}
	}

}