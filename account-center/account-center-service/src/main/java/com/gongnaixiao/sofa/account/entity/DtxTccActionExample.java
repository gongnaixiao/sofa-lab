package com.gongnaixiao.sofa.account.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DtxTccActionExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public DtxTccActionExample() {
        oredCriteria = new ArrayList<>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andActionIdIsNull() {
            addCriterion("action_id is null");
            return (Criteria) this;
        }

        public Criteria andActionIdIsNotNull() {
            addCriterion("action_id is not null");
            return (Criteria) this;
        }

        public Criteria andActionIdEqualTo(String value) {
            addCriterion("action_id =", value, "actionId");
            return (Criteria) this;
        }

        public Criteria andActionIdNotEqualTo(String value) {
            addCriterion("action_id <>", value, "actionId");
            return (Criteria) this;
        }

        public Criteria andActionIdGreaterThan(String value) {
            addCriterion("action_id >", value, "actionId");
            return (Criteria) this;
        }

        public Criteria andActionIdGreaterThanOrEqualTo(String value) {
            addCriterion("action_id >=", value, "actionId");
            return (Criteria) this;
        }

        public Criteria andActionIdLessThan(String value) {
            addCriterion("action_id <", value, "actionId");
            return (Criteria) this;
        }

        public Criteria andActionIdLessThanOrEqualTo(String value) {
            addCriterion("action_id <=", value, "actionId");
            return (Criteria) this;
        }

        public Criteria andActionIdLike(String value) {
            addCriterion("action_id like", value, "actionId");
            return (Criteria) this;
        }

        public Criteria andActionIdNotLike(String value) {
            addCriterion("action_id not like", value, "actionId");
            return (Criteria) this;
        }

        public Criteria andActionIdIn(List<String> values) {
            addCriterion("action_id in", values, "actionId");
            return (Criteria) this;
        }

        public Criteria andActionIdNotIn(List<String> values) {
            addCriterion("action_id not in", values, "actionId");
            return (Criteria) this;
        }

        public Criteria andActionIdBetween(String value1, String value2) {
            addCriterion("action_id between", value1, value2, "actionId");
            return (Criteria) this;
        }

        public Criteria andActionIdNotBetween(String value1, String value2) {
            addCriterion("action_id not between", value1, value2, "actionId");
            return (Criteria) this;
        }

        public Criteria andActionNameIsNull() {
            addCriterion("action_name is null");
            return (Criteria) this;
        }

        public Criteria andActionNameIsNotNull() {
            addCriterion("action_name is not null");
            return (Criteria) this;
        }

        public Criteria andActionNameEqualTo(String value) {
            addCriterion("action_name =", value, "actionName");
            return (Criteria) this;
        }

        public Criteria andActionNameNotEqualTo(String value) {
            addCriterion("action_name <>", value, "actionName");
            return (Criteria) this;
        }

        public Criteria andActionNameGreaterThan(String value) {
            addCriterion("action_name >", value, "actionName");
            return (Criteria) this;
        }

        public Criteria andActionNameGreaterThanOrEqualTo(String value) {
            addCriterion("action_name >=", value, "actionName");
            return (Criteria) this;
        }

        public Criteria andActionNameLessThan(String value) {
            addCriterion("action_name <", value, "actionName");
            return (Criteria) this;
        }

        public Criteria andActionNameLessThanOrEqualTo(String value) {
            addCriterion("action_name <=", value, "actionName");
            return (Criteria) this;
        }

        public Criteria andActionNameLike(String value) {
            addCriterion("action_name like", value, "actionName");
            return (Criteria) this;
        }

        public Criteria andActionNameNotLike(String value) {
            addCriterion("action_name not like", value, "actionName");
            return (Criteria) this;
        }

        public Criteria andActionNameIn(List<String> values) {
            addCriterion("action_name in", values, "actionName");
            return (Criteria) this;
        }

        public Criteria andActionNameNotIn(List<String> values) {
            addCriterion("action_name not in", values, "actionName");
            return (Criteria) this;
        }

        public Criteria andActionNameBetween(String value1, String value2) {
            addCriterion("action_name between", value1, value2, "actionName");
            return (Criteria) this;
        }

        public Criteria andActionNameNotBetween(String value1, String value2) {
            addCriterion("action_name not between", value1, value2, "actionName");
            return (Criteria) this;
        }

        public Criteria andTxIdIsNull() {
            addCriterion("tx_id is null");
            return (Criteria) this;
        }

        public Criteria andTxIdIsNotNull() {
            addCriterion("tx_id is not null");
            return (Criteria) this;
        }

        public Criteria andTxIdEqualTo(String value) {
            addCriterion("tx_id =", value, "txId");
            return (Criteria) this;
        }

        public Criteria andTxIdNotEqualTo(String value) {
            addCriterion("tx_id <>", value, "txId");
            return (Criteria) this;
        }

        public Criteria andTxIdGreaterThan(String value) {
            addCriterion("tx_id >", value, "txId");
            return (Criteria) this;
        }

        public Criteria andTxIdGreaterThanOrEqualTo(String value) {
            addCriterion("tx_id >=", value, "txId");
            return (Criteria) this;
        }

        public Criteria andTxIdLessThan(String value) {
            addCriterion("tx_id <", value, "txId");
            return (Criteria) this;
        }

        public Criteria andTxIdLessThanOrEqualTo(String value) {
            addCriterion("tx_id <=", value, "txId");
            return (Criteria) this;
        }

        public Criteria andTxIdLike(String value) {
            addCriterion("tx_id like", value, "txId");
            return (Criteria) this;
        }

        public Criteria andTxIdNotLike(String value) {
            addCriterion("tx_id not like", value, "txId");
            return (Criteria) this;
        }

        public Criteria andTxIdIn(List<String> values) {
            addCriterion("tx_id in", values, "txId");
            return (Criteria) this;
        }

        public Criteria andTxIdNotIn(List<String> values) {
            addCriterion("tx_id not in", values, "txId");
            return (Criteria) this;
        }

        public Criteria andTxIdBetween(String value1, String value2) {
            addCriterion("tx_id between", value1, value2, "txId");
            return (Criteria) this;
        }

        public Criteria andTxIdNotBetween(String value1, String value2) {
            addCriterion("tx_id not between", value1, value2, "txId");
            return (Criteria) this;
        }

        public Criteria andActionGroupIsNull() {
            addCriterion("action_group is null");
            return (Criteria) this;
        }

        public Criteria andActionGroupIsNotNull() {
            addCriterion("action_group is not null");
            return (Criteria) this;
        }

        public Criteria andActionGroupEqualTo(String value) {
            addCriterion("action_group =", value, "actionGroup");
            return (Criteria) this;
        }

        public Criteria andActionGroupNotEqualTo(String value) {
            addCriterion("action_group <>", value, "actionGroup");
            return (Criteria) this;
        }

        public Criteria andActionGroupGreaterThan(String value) {
            addCriterion("action_group >", value, "actionGroup");
            return (Criteria) this;
        }

        public Criteria andActionGroupGreaterThanOrEqualTo(String value) {
            addCriterion("action_group >=", value, "actionGroup");
            return (Criteria) this;
        }

        public Criteria andActionGroupLessThan(String value) {
            addCriterion("action_group <", value, "actionGroup");
            return (Criteria) this;
        }

        public Criteria andActionGroupLessThanOrEqualTo(String value) {
            addCriterion("action_group <=", value, "actionGroup");
            return (Criteria) this;
        }

        public Criteria andActionGroupLike(String value) {
            addCriterion("action_group like", value, "actionGroup");
            return (Criteria) this;
        }

        public Criteria andActionGroupNotLike(String value) {
            addCriterion("action_group not like", value, "actionGroup");
            return (Criteria) this;
        }

        public Criteria andActionGroupIn(List<String> values) {
            addCriterion("action_group in", values, "actionGroup");
            return (Criteria) this;
        }

        public Criteria andActionGroupNotIn(List<String> values) {
            addCriterion("action_group not in", values, "actionGroup");
            return (Criteria) this;
        }

        public Criteria andActionGroupBetween(String value1, String value2) {
            addCriterion("action_group between", value1, value2, "actionGroup");
            return (Criteria) this;
        }

        public Criteria andActionGroupNotBetween(String value1, String value2) {
            addCriterion("action_group not between", value1, value2, "actionGroup");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(String value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("status like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("status not like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andParamDataIsNull() {
            addCriterion("param_data is null");
            return (Criteria) this;
        }

        public Criteria andParamDataIsNotNull() {
            addCriterion("param_data is not null");
            return (Criteria) this;
        }

        public Criteria andParamDataEqualTo(String value) {
            addCriterion("param_data =", value, "paramData");
            return (Criteria) this;
        }

        public Criteria andParamDataNotEqualTo(String value) {
            addCriterion("param_data <>", value, "paramData");
            return (Criteria) this;
        }

        public Criteria andParamDataGreaterThan(String value) {
            addCriterion("param_data >", value, "paramData");
            return (Criteria) this;
        }

        public Criteria andParamDataGreaterThanOrEqualTo(String value) {
            addCriterion("param_data >=", value, "paramData");
            return (Criteria) this;
        }

        public Criteria andParamDataLessThan(String value) {
            addCriterion("param_data <", value, "paramData");
            return (Criteria) this;
        }

        public Criteria andParamDataLessThanOrEqualTo(String value) {
            addCriterion("param_data <=", value, "paramData");
            return (Criteria) this;
        }

        public Criteria andParamDataLike(String value) {
            addCriterion("param_data like", value, "paramData");
            return (Criteria) this;
        }

        public Criteria andParamDataNotLike(String value) {
            addCriterion("param_data not like", value, "paramData");
            return (Criteria) this;
        }

        public Criteria andParamDataIn(List<String> values) {
            addCriterion("param_data in", values, "paramData");
            return (Criteria) this;
        }

        public Criteria andParamDataNotIn(List<String> values) {
            addCriterion("param_data not in", values, "paramData");
            return (Criteria) this;
        }

        public Criteria andParamDataBetween(String value1, String value2) {
            addCriterion("param_data between", value1, value2, "paramData");
            return (Criteria) this;
        }

        public Criteria andParamDataNotBetween(String value1, String value2) {
            addCriterion("param_data not between", value1, value2, "paramData");
            return (Criteria) this;
        }

        public Criteria andGmtCreateIsNull() {
            addCriterion("gmt_create is null");
            return (Criteria) this;
        }

        public Criteria andGmtCreateIsNotNull() {
            addCriterion("gmt_create is not null");
            return (Criteria) this;
        }

        public Criteria andGmtCreateEqualTo(Date value) {
            addCriterion("gmt_create =", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateNotEqualTo(Date value) {
            addCriterion("gmt_create <>", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateGreaterThan(Date value) {
            addCriterion("gmt_create >", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateGreaterThanOrEqualTo(Date value) {
            addCriterion("gmt_create >=", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateLessThan(Date value) {
            addCriterion("gmt_create <", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateLessThanOrEqualTo(Date value) {
            addCriterion("gmt_create <=", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateIn(List<Date> values) {
            addCriterion("gmt_create in", values, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateNotIn(List<Date> values) {
            addCriterion("gmt_create not in", values, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateBetween(Date value1, Date value2) {
            addCriterion("gmt_create between", value1, value2, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateNotBetween(Date value1, Date value2) {
            addCriterion("gmt_create not between", value1, value2, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedIsNull() {
            addCriterion("gmt_modified is null");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedIsNotNull() {
            addCriterion("gmt_modified is not null");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedEqualTo(Date value) {
            addCriterion("gmt_modified =", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedNotEqualTo(Date value) {
            addCriterion("gmt_modified <>", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedGreaterThan(Date value) {
            addCriterion("gmt_modified >", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedGreaterThanOrEqualTo(Date value) {
            addCriterion("gmt_modified >=", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedLessThan(Date value) {
            addCriterion("gmt_modified <", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedLessThanOrEqualTo(Date value) {
            addCriterion("gmt_modified <=", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedIn(List<Date> values) {
            addCriterion("gmt_modified in", values, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedNotIn(List<Date> values) {
            addCriterion("gmt_modified not in", values, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedBetween(Date value1, Date value2) {
            addCriterion("gmt_modified between", value1, value2, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedNotBetween(Date value1, Date value2) {
            addCriterion("gmt_modified not between", value1, value2, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andShardingKeyIsNull() {
            addCriterion("sharding_key is null");
            return (Criteria) this;
        }

        public Criteria andShardingKeyIsNotNull() {
            addCriterion("sharding_key is not null");
            return (Criteria) this;
        }

        public Criteria andShardingKeyEqualTo(String value) {
            addCriterion("sharding_key =", value, "shardingKey");
            return (Criteria) this;
        }

        public Criteria andShardingKeyNotEqualTo(String value) {
            addCriterion("sharding_key <>", value, "shardingKey");
            return (Criteria) this;
        }

        public Criteria andShardingKeyGreaterThan(String value) {
            addCriterion("sharding_key >", value, "shardingKey");
            return (Criteria) this;
        }

        public Criteria andShardingKeyGreaterThanOrEqualTo(String value) {
            addCriterion("sharding_key >=", value, "shardingKey");
            return (Criteria) this;
        }

        public Criteria andShardingKeyLessThan(String value) {
            addCriterion("sharding_key <", value, "shardingKey");
            return (Criteria) this;
        }

        public Criteria andShardingKeyLessThanOrEqualTo(String value) {
            addCriterion("sharding_key <=", value, "shardingKey");
            return (Criteria) this;
        }

        public Criteria andShardingKeyLike(String value) {
            addCriterion("sharding_key like", value, "shardingKey");
            return (Criteria) this;
        }

        public Criteria andShardingKeyNotLike(String value) {
            addCriterion("sharding_key not like", value, "shardingKey");
            return (Criteria) this;
        }

        public Criteria andShardingKeyIn(List<String> values) {
            addCriterion("sharding_key in", values, "shardingKey");
            return (Criteria) this;
        }

        public Criteria andShardingKeyNotIn(List<String> values) {
            addCriterion("sharding_key not in", values, "shardingKey");
            return (Criteria) this;
        }

        public Criteria andShardingKeyBetween(String value1, String value2) {
            addCriterion("sharding_key between", value1, value2, "shardingKey");
            return (Criteria) this;
        }

        public Criteria andShardingKeyNotBetween(String value1, String value2) {
            addCriterion("sharding_key not between", value1, value2, "shardingKey");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}