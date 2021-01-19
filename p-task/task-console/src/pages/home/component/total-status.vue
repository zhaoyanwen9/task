<template>
    <div>
        <el-row class="grow">
            <el-col :md="2" :lg="4" class="grow__item">
                <div class="grow__item--detail clearfix">
                    <div class="grow__item--icon iconfont icon-home"></div>
                    <div>
                        
                        1.
                        <p class="grow__item--number">￥455025元</p>
                        <p class="grow__item--type">本月销售总额</p>
                    </div>
                    
                </div>
            </el-col>
        </el-row>
    </div>
</template>

<script>
import axios from '@/config/httpConfig'
export default {
    data() {
        return {}
    },
    created() {
            this.getTableData();
        },
    methods: {
            getTableData() {
                debugger;
                axios.get('/api/rest/redis/util/v1/redisGetAllKeyValue').then(response => {
                    debugger
                    
                }).catch(function (error) {
                    console.log(error);
                });
            }
        }
}
</script>

<style lang="scss">
.grow {
    &__item {
        &--detail{
            >div{
                float: left;
            }
        }
        &--number{
            color:#374761;
            font-size: 30px;
        }
        &--percentage {
            height: 44px;
            line-height: 44px;
            background: #364860;
            color: #fff;
        }
    }
}
</style>
