package com.uyou.copenaccount.response;


import com.uyou.copenaccount.base.BaseResponse;
import com.uyou.copenaccount.utils.StringUtils;

/**
 * Created by zdd on 2019/4/22.
 * <p>
 * Description:
 */
public class QueryIncomeResponse extends BaseResponse {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        private String allDayMoney;
        private String todayMoney;
        private String yesterdayMoney;

        public String getAllDayMoney() {
            return StringUtils.checkNull(allDayMoney);
        }

        public void setAllDayMoney(String allDayMoney) {
            this.allDayMoney = allDayMoney;
        }

        public String getTodayMoney() {
            return StringUtils.checkNull(todayMoney);
        }

        public void setTodayMoney(String todayMoney) {
            this.todayMoney = todayMoney;
        }

        public String getYesterdayMoney() {
            return StringUtils.checkNull(yesterdayMoney);
        }

        public void setYesterdayMoney(String yesterdayMoney) {
            this.yesterdayMoney = yesterdayMoney;
        }

    }

}
