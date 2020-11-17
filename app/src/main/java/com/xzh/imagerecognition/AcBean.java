package com.xzh.imagerecognition;

/**
 * Created by Lenovo on 2020/9/18.
 */
public class AcBean {
    /**
     * refresh_token : 25.3c14e3384abb159497025c870776d293.315360000.1915767436.282335-20576638
     * expires_in : 2592000
     * session_key : 9mzdAvRSr6aWKfVrWkDVvrvdg8i2HXXINYbiCf1oVY6SThWKtEoOTwJ7VvU1tmJxvlun/tfmRIIF+rN9cwP9smmdbziiCw==
     * access_token : 24.966eebccce5f5ed993a5cd83bb2d0cb9.2592000.1602999436.282335-20576638
     * scope : public vis-classify_dishes vis-classify_car brain_all_scope vis-classify_animal vis-classify_plant brain_object_detect brain_realtime_logo brain_dish_detect brain_car_detect brain_animal_classify brain_plant_classify brain_ingredient brain_advanced_general_classify brain_custom_dish brain_poi_recognize brain_vehicle_detect brain_redwine brain_currency brain_vehicle_damage wise_adapt lebo_resource_base lightservice_public hetu_basic lightcms_map_poi kaidian_kaidian ApsMisTest_Test权限 vis-classify_flower lpq_开放 cop_helloScope ApsMis_fangdi_permission smartapp_snsapi_base iop_autocar oauth_tp_app smartapp_smart_game_openapi oauth_sessionkey smartapp_swanid_verify smartapp_opensource_openapi smartapp_opensource_recapi fake_face_detect_开放Scope vis-ocr_虚拟人物助理 idl-video_虚拟人物助理
     * session_secret : 638ea23343f8e161a5ab07562289c9cb
     */

    private int expires_in;
    private String access_token;

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
}
