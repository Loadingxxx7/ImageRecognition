package com.xzh.imagerecognition;

import java.util.List;

/**
 * Created by Lenovo on 2020/9/18.
 */
public class DataBean {
    /**
     * log_id : 1287348777818059826
     * result_num : 5
     * result : [{"score":0.97422099113464,"name":"苹果"},{"score":0.020999869331717,"name":"蛇果"},{"score":0.001145027927123,"name":"非果蔬食材"},{"score":8.6209387518466E-4,"name":"梨"},{"score":5.7713658316061E-4,"name":"翠伏梨"}]
     */

    private String log_id;
    private int result_num;
    private List<ResultBean> result;

    public String getLog_id() {
        return log_id;
    }

    public void setLog_id(String log_id) {
        this.log_id = log_id;
    }

    public int getResult_num() {
        return result_num;
    }

    public void setResult_num(int result_num) {
        this.result_num = result_num;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * score : 0.97422099113464
         * name : 苹果
         */

        private double score;
        private String name;

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
