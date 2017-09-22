package com.android.pgb.Utils;

import com.android.pgb.Bean.ProvinceBean;

import java.util.ArrayList;

/**
 * Created by kanghui on 2017/1/18.
 */

public class LogicHelp {


    /***
     * 返回状态标志
     *
     * @param zt
     * @return
     */
    public static String getBJZT(String zt) {

        String temp = "";
        switch (zt) {
            case "-2":
                temp = "终止询价";
                break;
            case "-1":
                temp = "未付款";
                break;
            case "0":
                temp = "询价中";
                break;
            case "1":
                temp = "结束询价";
                break;
            case "2":
                temp = "已完成";
                break;
            case "3":
                temp = "过期处理";
                break;
        }

        return temp;
    }


    /***
     * 返回采纳状态
     *
     * @param zt
     * @return
     */
    public static String getCNBZ(String zt) {

        String temp = "";
        switch (zt) {
            case "-1":
                temp = "询价已取消";
                break;
            case "0":
                temp = "未采纳";
                break;
            case "1":
                temp = "采纳";
                break;
        }

        return temp;
    }


    public static String getCityFromStr(String TempStr, ArrayList<ProvinceBean> CityarrayList,ArrayList<ArrayList<ProvinceBean>> city_two) {

        String city = "";
        for (int i=0;i<CityarrayList.size();i++) {
            if (TempStr.indexOf(CityarrayList.get(i).getName()) > -1) {

                for(ProvinceBean pb : city_two.get(i)) {
                    if (TempStr.indexOf(pb.getName()) > -1)

                        city = CityarrayList.get(i).getName()+pb.getName();
                }

            }

        }


        return city;


    }


}
