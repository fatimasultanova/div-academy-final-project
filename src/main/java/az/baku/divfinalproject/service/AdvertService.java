package az.baku.divfinalproject.service;
import az.baku.divfinalproject.dto.request.AdvertRequest;
import az.baku.divfinalproject.dto.response.AdvertResponse;

import java.util.List;


public interface AdvertService extends CrudService<AdvertRequest, AdvertResponse>{
    List<AdvertResponse> getAdverts(int userId);
}
