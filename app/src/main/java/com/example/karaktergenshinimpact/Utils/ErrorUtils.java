package com.example.karaktergenshinimpact.Utils;

import com.example.karaktergenshinimpact.activity.AddCharacterActivity;
import com.example.karaktergenshinimpact.request.APIService;
import com.example.karaktergenshinimpact.response.APIError;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

public class ErrorUtils {
    public static APIError parseError(Response<?> response) {
        Converter<ResponseBody, APIError> converter =
               APIService.getRetrofitInstance()
                        .responseBodyConverter(APIError.class, new Annotation[0]);

        APIError error;

        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new APIError();
        }

        return error;
    }
}
