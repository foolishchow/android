package retrofit2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

import static retrofit2.Utils.methodError;

/**
 * Description:
 * Author: foolishchow
 * Date: 29/12/2020 5:01 PM
 */
public class RetrofitHack {


    private static Gson gson = null;

    static {
        gson = new Gson();
    }

    @Nullable
    public static <T> JSONObject toJson(@Nullable T value) {
        if (value == null) return null;
        try {
            return new JSONObject(gson.toJson(value));
        } catch (JSONException e) {
            return null;
        }
    }


    //todo this is external
    public static class QueryBean<T> extends RequestAction<T> {
        private final Converter<?, String> converter;
        private Type parameterType;
        private static final Charset UTF_8 = StandardCharsets.UTF_8;

        public QueryBean(Type parameterType, Converter<?, String> converter) {
            this.converter = converter;
            this.parameterType = parameterType;
        }

        @Override
        void perform(RequestBuilder builder, T value) throws IOException {
            JSONObject jsonObject = toJson(value);
            if (jsonObject == null) {
                return;
            }
            try {
                Iterator<String> iterator = jsonObject.keys();
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    String valueString = new String(jsonObject.getString(key).getBytes(), StandardCharsets.UTF_8); //意思是以UTF-8的编码生成字符串
                    builder.addQueryParam(key, valueString, true);
                }

            } catch (Exception ignored) {

            }
        }
    }

    @NonNull
    public static RequestAction getRequestAction(
            Method method,
            Retrofit retrofit, int i, Type parameterType, Annotation[] parameterAnnotations, Class<?> rawParameterType) {
        RequestAction action;
        if (Iterable.class.isAssignableFrom(rawParameterType)) {
            if (!(parameterType instanceof ParameterizedType)) {
                throw parameterError(method, i, rawParameterType.getSimpleName()
                        + " must include generic type (e.g., "
                        + rawParameterType.getSimpleName()
                        + "<String>)");
            }
            ParameterizedType parameterizedType = (ParameterizedType) parameterType;
            Type iterableType = Utils.getParameterUpperBound(0, parameterizedType);
            Converter<?, String> converter =
                    retrofit.stringConverter(iterableType, parameterAnnotations);
            action = new QueryBean<>(parameterType, converter).iterable();
        } else if (rawParameterType.isArray()) {
          Class<?> componentType = rawParameterType.getComponentType();
          Class<?> arrayComponentType = boxIfPrimitive(componentType);
            Converter<?, String> converter =
                    retrofit.stringConverter(arrayComponentType, parameterAnnotations);
            action = new QueryBean<>(parameterType, converter).array();
        } else {
            Converter<?, String> converter =
                    retrofit.stringConverter(parameterType, parameterAnnotations);
            action = new QueryBean<>(parameterType, converter);
        }
        return action;
    }


    private static RuntimeException parameterError(Method method, int index, String message, Object... args) {
        return methodError(method, message + " (parameter #" + (index + 1) + ")", args);
    }


    private static Class<?> boxIfPrimitive(Class<?> type) {
        if (boolean.class == type) return Boolean.class;
        if (byte.class == type) return Byte.class;
        if (char.class == type) return Character.class;
        if (double.class == type) return Double.class;
        if (float.class == type) return Float.class;
        if (int.class == type) return Integer.class;
        if (long.class == type) return Long.class;
        if (short.class == type) return Short.class;
        return type;
    }
}
