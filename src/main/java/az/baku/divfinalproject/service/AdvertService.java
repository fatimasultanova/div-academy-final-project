package az.baku.divfinalproject.service;
import az.baku.divfinalproject.dto.request.AdvertRequest;
import az.baku.divfinalproject.dto.request.Request;
import az.baku.divfinalproject.dto.response.AdvertResponse;
import az.baku.divfinalproject.dto.response.UserResponse;
import az.baku.divfinalproject.entity.Advert;

import java.util.List;


public interface AdvertService extends CrudService<AdvertRequest, AdvertResponse>{
    List<AdvertResponse> getAdverts(long userId);
    List<Advert> findAllByAmountMonthlyIsLessThanEqual(double amount);
    List<Advert> findAllByAdvertTypeId(long advertTypeId);
    UserResponse getContactInformation(Request<AdvertRequest> request);
}
