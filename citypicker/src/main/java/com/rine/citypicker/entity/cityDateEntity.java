package com.rine.citypicker.entity;

import java.util.List;

/**
 * 城市数据实例
 * @author rine
 * @version 1.0(2019/1/15)
 */
public class cityDateEntity {

    /**
     * tag : A
     * cities : [{"id":"210300","name":"鞍山市","dis":[{"disid":"210302","disname":"铁东区"},{"disid":"210303","disname":"铁西区"},{"disid":"210304","disname":"立山区"},{"disid":"210311","disname":"千山区"},{"disid":"210321","disname":"台安县"},{"disid":"210323","disname":"岫岩满族自治县"},{"disid":"210381","disname":"海城市"}]},{"id":"340800","name":"安庆市","dis":[{"disid":"340802","disname":"迎江区"},{"disid":"340803","disname":"大观区"},{"disid":"340811","disname":"宜秀区"},{"disid":"340822","disname":"怀宁县"},{"disid":"340823","disname":"枞阳县"},{"disid":"340824","disname":"潜山县"},{"disid":"340825","disname":"太湖县"},{"disid":"340826","disname":"宿松县"},{"disid":"340827","disname":"望江县"},{"disid":"340828","disname":"岳西县"},{"disid":"340881","disname":"桐城市"}]},{"id":"410500","name":"安阳市","dis":[{"disid":"410502","disname":"文峰区"},{"disid":"410503","disname":"北关区"},{"disid":"410505","disname":"殷都区"},{"disid":"410506","disname":"龙安区"},{"disid":"410522","disname":"安阳县"},{"disid":"410523","disname":"汤阴县"},{"disid":"410526","disname":"滑县"},{"disid":"410527","disname":"内黄县"},{"disid":"410581","disname":"林州市"}]},{"id":"152900","name":"阿拉善盟","dis":[{"disid":"152921","disname":"阿拉善左旗"},{"disid":"152922","disname":"阿拉善右旗"},{"disid":"152923","disname":"额济纳旗"}]},{"id":"520400","name":"安顺市","dis":[{"disid":"520402","disname":"西秀区"},{"disid":"520421","disname":"平坝县"},{"disid":"520422","disname":"普定县"},{"disid":"520423","disname":"镇宁布依族苗族自治县"},{"disid":"520424","disname":"关岭布依族苗族自治县"},{"disid":"520425","disname":"紫云苗族布依族自治县"}]},{"id":"542500","name":"阿里地区","dis":[{"disid":"542521","disname":"普兰县"},{"disid":"542522","disname":"札达县"},{"disid":"542523","disname":"噶尔县"},{"disid":"542524","disname":"日土县"},{"disid":"542525","disname":"革吉县"},{"disid":"542526","disname":"改则县"},{"disid":"542527","disname":"措勤县"}]},{"id":"610900","name":"安康市","dis":[{"disid":"610902","disname":"汉滨区"},{"disid":"610921","disname":"汉阴县"},{"disid":"610922","disname":"石泉县"},{"disid":"610923","disname":"宁陕县"},{"disid":"610924","disname":"紫阳县"},{"disid":"610925","disname":"岚皋县"},{"disid":"610926","disname":"平利县"},{"disid":"610927","disname":"镇坪县"},{"disid":"610928","disname":"旬阳县"},{"disid":"610929","disname":"白河县"}]},{"id":"652900","name":"阿克苏地区","dis":[{"disid":"652901","disname":"阿克苏市"},{"disid":"652922","disname":"温宿县"},{"disid":"652923","disname":"库车县"},{"disid":"652924","disname":"沙雅县"},{"disid":"652925","disname":"新和县"},{"disid":"652926","disname":"拜城县"},{"disid":"652927","disname":"乌什县"},{"disid":"652928","disname":"阿瓦提县"},{"disid":"652929","disname":"柯坪县"}]},{"id":"513200","name":"阿坝藏族羌族自治州","dis":[{"disid":"513221","disname":"汶川县"},{"disid":"513222","disname":"理县"},{"disid":"513223","disname":"茂县"},{"disid":"513224","disname":"松潘县"},{"disid":"513225","disname":"九寨沟县"},{"disid":"513226","disname":"金川县"},{"disid":"513227","disname":"小金县"},{"disid":"513228","disname":"黑水县"},{"disid":"513229","disname":"马尔康县"},{"disid":"513230","disname":"壤塘县"},{"disid":"513231","disname":"阿坝县"},{"disid":"513232","disname":"若尔盖县"},{"disid":"513233","disname":"红原县"}]},{"id":"654300","name":"阿勒泰地区","dis":[{"disid":"654301","disname":"阿勒泰市"},{"disid":"654321","disname":"布尔津县"},{"disid":"654322","disname":"富蕴县"},{"disid":"654323","disname":"福海县"},{"disid":"654324","disname":"哈巴河县"},{"disid":"654325","disname":"青河县"},{"disid":"654326","disname":"吉木乃县"}]}]
     */

    private String tag;
    private List<CitiesBean> cities;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public List<CitiesBean> getCities() {
        return cities;
    }

    public void setCities(List<CitiesBean> cities) {
        this.cities = cities;
    }

    public static class CitiesBean {
        /**
         * id : 210300
         * name : 鞍山市
         * dis : [{"disid":"210302","disname":"铁东区"},{"disid":"210303","disname":"铁西区"},{"disid":"210304","disname":"立山区"},{"disid":"210311","disname":"千山区"},{"disid":"210321","disname":"台安县"},{"disid":"210323","disname":"岫岩满族自治县"},{"disid":"210381","disname":"海城市"}]
         */

        private String id;
        private String name;
        private List<DisBean> dis;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<DisBean> getDis() {
            return dis;
        }

        public void setDis(List<DisBean> dis) {
            this.dis = dis;
        }

        public static class DisBean {
            /**
             * disid : 210302
             * disname : 铁东区
             */

            private String disid;
            private String disname;

            public String getDisid() {
                return disid;
            }

            public void setDisid(String disid) {
                this.disid = disid;
            }

            public String getDisname() {
                return disname;
            }

            public void setDisname(String disname) {
                this.disname = disname;
            }
        }
    }
}
