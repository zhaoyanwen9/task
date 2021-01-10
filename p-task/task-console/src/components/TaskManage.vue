<template>
    <div>
        <el-row>
            <el-col :span="6">
                <el-button @click="drawer = true" type="primary" icon="el-icon-plus" size="mini" plain>
                    创建任务
                </el-button>
                <el-button @click="getTableData" type="primary" icon="el-icon-refresh" size="mini" plain></el-button>
            </el-col>
            <el-col :span="18"></el-col>
        </el-row>
        <el-row>
            <el-col :span="24">
                <div style="height: 10px;"></div>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24">
                <el-table
                        :data="tableData"
                        style="width: 100%"
                        height="700"
                        border
                        size="mini"
                        v-loading="loading"
                        element-loading-text="拼命加载中"
                        element-loading-spinner="el-icon-loading"
                        element-loading-background="rgba(0, 0, 0, 0.8)">
                    <el-table-column type="expand" width="40">
                        <template slot-scope="props">
                            <el-timeline>
                                <el-timeline-item
                                        v-for="(activity, index) in props.row.activities"
                                        :key="index"
                                        :icon="activity.icon"
                                        :type="activity.type"
                                        :color="activity.color"
                                        :size="activity.size"
                                        :timestamp="activity.timestamp">
                                    {{activity.content}}
                                </el-timeline-item>
                            </el-timeline>
                        </template>
                    </el-table-column>
                    <el-table-column label="id" prop="id" width="60"></el-table-column>
                    <el-table-column label="操作" align="right" width="90">
                        <template slot-scope="scope">
                            <el-tooltip class="item" effect="dark" content="编辑" placement="top">
                                <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">
                                    <i class="el-icon-edit"></i>
                                </el-button>
                            </el-tooltip>
                            <el-tooltip class="item" effect="dark" content="删除" placement="top">
                                <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">
                                    <i class="el-icon-delete"></i>
                                </el-button>
                            </el-tooltip>
                        </template>
                    </el-table-column>
                    <el-table-column label=名称 prop="name" width="50" show-overflow-tooltip>
                        <template scope="scope">
                            <el-link :href="scope.row.name" target="_blank">{{scope.row.name}}</el-link>
                        </template>
                    </el-table-column>
                    <el-table-column label="类型" prop="type" width="50"></el-table-column>
                    <el-table-column label="状态" prop="status" width="50">
                        <template scope="scope">
                            <el-tag effect="dark" size="mini" type="info" v-if="scope.row.status == 0">
                                <el-tooltip class="item" effect="dark" content="未启动" placement="top">
                                    <i class="el-icon-lock"></i>
                                </el-tooltip>
                            </el-tag>
                            <el-tag effect="dark" size="mini" v-else-if="scope.row.status == 1">
                                <el-tooltip class="item" effect="dark" content="运行中" placement="top">
                                    <i class="el-icon-loading"></i>
                                </el-tooltip>
                            </el-tag>
                            <el-tag effect="dark" size="mini" type="success" v-else-if="scope.row.status == 2">
                                <el-tooltip class="item" effect="dark" content="成功" placement="top">
                                    <i class="el-icon-success"></i>
                                </el-tooltip>
                            </el-tag>
                            <el-tag effect="dark" size="mini" type="danger" v-else-if="scope.row.status == 3">
                                <el-tooltip class="item" effect="dark" content="失败" placement="top">
                                    <i class="el-icon-error"></i>
                                </el-tooltip>
                            </el-tag>
                            <el-tag effect="dark" size="mini" type="warning" v-else>
                                <el-tooltip class="item" effect="dark" content="异常" placement="top">
                                    <i class="el-icon-lightning"></i>
                                </el-tooltip>
                            </el-tag>
                        </template>
                    </el-table-column>
                    <el-table-column label="创建时间" prop="cTime" width="70" show-overflow-tooltip></el-table-column>
                    <el-table-column label="开始时间" prop="sTime" width="70"></el-table-column>
                    <el-table-column label="结束时间" prop="eTime" width="70"></el-table-column>
                    <el-table-column label="进度" prop="progress" width="160">
                        <template scope="scope">
                            <div v-if="scope.row.progress == 100">
                                <el-progress :percentage="scope.row.progress" status="success"></el-progress>
                            </div>
                            <div v-else>
                                <el-progress :percentage="scope.row.progress" :color="colorMethod"></el-progress>
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column label="启动方式" prop="startStyle" width="70"></el-table-column>
                    <el-table-column label="启动参数" prop="startParam" width="70"></el-table-column>
                    <el-table-column label="服务ID" prop="serviceId" width="60"></el-table-column>
                    <el-table-column label="环境类型" prop="envType" width="70"></el-table-column>
                    <el-table-column label="环境ID" prop="envId" width="60"></el-table-column>
                    <el-table-column label="故障ID" prop="faultId" width="70"></el-table-column>
                    <el-table-column label="用例ID" prop="caseId" width="60"></el-table-column>
                    <el-table-column label="失败原因" prop="failReason" width="70"></el-table-column>
                    <el-table-column label="执行日志" prop="executeLog" width="70"></el-table-column>
                    <el-table-column label="是否删除" prop="isDelete" width="70">
                        <template scope="scope">
                            {{scope.row.isDelete}}
                        </template>
                    </el-table-column>
                </el-table>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24">
                <el-pagination
                        @size-change="handleSizeChange"
                        @current-change="handleCurrentChange"
                        :current-page="currentPage"
                        :page-sizes="[5, 10, 15, 30, 50]"
                        :page-size="pageSize"
                        layout="total, sizes, prev, pager, next, jumper"
                        :total="total">
                </el-pagination>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24">
                <el-drawer
                        title="创建任务"
                        :visible.sync="drawer"
                        size="45%">
                    <div>
                        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="80px"
                                 class="demo-ruleForm" :label-position="labelPosition" align="left" size="mini">
                            <fieldset class="fieldsetStyle">
                                <legend align="left" class="legendStyle">任务信息</legend>
                                <el-row>
                                    <el-col :span="12">
                                        <el-form-item label="服务" prop="service_id">
                                            <el-select v-model="ruleForm.service_id" placeholder="请选择服务">
                                                <el-option
                                                        v-for="item in serviceArr"
                                                        :key="item.value"
                                                        :label="item.label"
                                                        :value="item.value">
                                                </el-option>
                                            </el-select>
                                        </el-form-item>
                                    </el-col>
                                    <el-col :span="12"></el-col>
                                </el-row>
                                <el-row>
                                    <el-col :span="12">
                                        <el-form-item label="任务名称" prop="name">
                                            <el-input v-model="ruleForm.name"
                                                      placeholder="请输入任务名称"></el-input>
                                        </el-form-item>
                                    </el-col>
                                    <el-col :span="12"></el-col>
                                </el-row>
                                <el-row>
                                    <el-col :span="24">
                                        <el-form-item label="启动方式" prop="start_style">
                                            <el-radio-group v-model="ruleForm.start_style">
                                                <el-radio :label="1">aip</el-radio>
                                                <el-radio :label="2">trigger</el-radio>
                                            </el-radio-group>
                                        </el-form-item>
                                    </el-col>
                                </el-row>
                                <div v-if="ruleForm.start_style == 1">
                                    <el-col :span="12">
                                        <el-form-item
                                                label="时长"
                                                prop="dTime">
                                            <el-input type="age" v-model.number="ruleForm.dTime"
                                                      autocomplete="off"></el-input>
                                        </el-form-item>
                                    </el-col>
                                    <el-col :span="12"></el-col>
                                </div>
                                <div v-else-if="ruleForm.start_style == 2">
                                    <el-row>
                                        <el-col :span="24">
                                            <el-form-item label="周" prop="days">
                                                <el-checkbox-group v-model="ruleForm.days"
                                                                   :min="1"
                                                                   :max="7">
                                                    <el-checkbox v-for="day in weeks" :label="day" :key="day">
                                                        {{day}}
                                                    </el-checkbox>
                                                </el-checkbox-group>
                                            </el-form-item>
                                        </el-col>
                                    </el-row>
                                    <el-row>
                                        <el-col :span="12">
                                            <el-form-item label="开始时间" prop="sDate" required>
                                                <el-col :span="24">
                                                    <el-date-picker
                                                            v-model="ruleForm.sDate"
                                                            type="datetime"
                                                            placeholder="选择日期时间">
                                                    </el-date-picker>
                                                </el-col>
                                            </el-form-item>
                                        </el-col>
                                        <el-col :span="12">
                                            <el-form-item label="结束时间" prop="eDate" required>
                                                <el-col :span="24">
                                                    <el-date-picker
                                                            v-model="ruleForm.eDate"
                                                            type="datetime"
                                                            placeholder="选择日期时间"
                                                            default-time="12:00:00">
                                                    </el-date-picker>
                                                </el-col>
                                            </el-form-item>
                                        </el-col>
                                    </el-row>
                                </div>
                            </fieldset>
                            <fieldset class="fieldsetStyle">
                                <legend align="left" class="legendStyle">环境信息</legend>
                                <el-row>
                                    <el-col :span="12">
                                        <el-form-item label="环境组" prop="env_id">
                                            <el-select v-model="ruleForm.env_id" placeholder="请选择环境组">
                                                <el-option label="FRS-乌兰察布三" value="1"></el-option>
                                                <el-option label="DLG-乌兰察布一" value="2"></el-option>
                                            </el-select>
                                        </el-form-item>
                                    </el-col>
                                    <el-col :span="12">
                                    </el-col>
                                </el-row>
                            </fieldset>
                            <fieldset class="fieldsetStyle">
                                <legend align="left" class="legendStyle">故障信息</legend>
                                <el-row>
                                    <el-col :span="24">
                                        <el-form-item label="故障" prop="fault_id">
                                            <el-select v-model="ruleForm.fault_id" placeholder="请选择故障">
                                                <el-option label="CPU" value="1"></el-option>
                                                <el-option label="NET" value="2"></el-option>
                                            </el-select>
                                        </el-form-item>
                                    </el-col>
                                </el-row>
                            </fieldset>
                            <fieldset class="fieldsetStyle">
                                <legend align="left" class="legendStyle">用例信息</legend>
                                <el-row>
                                    <el-col :span="24">
                                        <el-form-item label="测试用例" prop="case_id">
                                            <el-select v-model="ruleForm.case_id" placeholder="请选择用例">
                                                <el-option label="Jenkins" value="1"></el-option>
                                                <el-option label="RF" value="2"></el-option>
                                            </el-select>
                                        </el-form-item>
                                    </el-col>
                                </el-row>
                            </fieldset>
                            <el-row>
                                <el-col :span="12">
                                    <el-form-item>
                                        <el-button type="primary" @click="submitForm('ruleForm')">立即创建
                                        </el-button>
                                        <el-button @click="resetForm('ruleForm')">重置</el-button>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="12"></el-col>
                            </el-row>
                        </el-form>
                    </div>
                </el-drawer>
            </el-col>
        </el-row>
    </div>
</template>

<script>
    const weekOptions = ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
    export default {
        name: "TaskManage",
        data() {
            return {
                loading: true,
                tableData: [],
                currentPage: 1,
                pageSize: 15,
                total: 0,
                drawer: false,
                labelPosition: 'right',
                weeks: weekOptions,
                serviceArr: [{
                    value: '1',
                    label: 'FRS'
                }, {
                    value: '2',
                    label: 'DLV'
                }, {
                    value: '3',
                    label: 'RES'
                }, {
                    value: '4',
                    label: 'DLG'
                }, {
                    value: '5',
                    label: 'CSS'
                }],
                ruleForm: {
                    service_id: '1',
                    name: 'Task',
                    start_style: 1,
                    dTime: 5,
                    days: [],
                    sDate: '',
                    eDate: '',
                    env_id: '1',
                    fault_id: '1',
                    case_id: '1'
                },
                rules: {
                    service_id: [
                        {required: true, message: '请选择服务', trigger: 'change'}
                    ],
                    name: [
                        {required: true, message: '请输入任务名称', trigger: 'blur'},
                        {min: 1, max: 255, message: '长度在 1 到 255 个字符', trigger: 'blur'}
                    ],
                    start_style: [
                        {required: true, message: '请选择启动方式', trigger: 'change'}
                    ],
                    dTime: [
                        {required: true, message: '时长不能为空'},
                        {type: 'number', message: '时长必须为数字值'}
                    ],
                    days: [
                        {type: 'array', required: true, message: '请至少选择一项', trigger: 'change'}
                    ],
                    sDate: [
                        {type: 'date', required: true, message: '请选择开始时间', trigger: 'blur'}
                    ],
                    eDate: [
                        {type: 'date', required: true, message: '请选择结束时间', trigger: 'blur'}
                    ],
                    env_id: [
                        {required: true, message: '请选择环境组', trigger: 'change'}
                    ],
                    fault_id: [
                        {required: true, message: '请选择故障', trigger: 'change'}
                    ],
                    case_id: [
                        {required: true, message: '请选择测试用例', trigger: 'change'}
                    ],
                },
            }
        },
        created() {
            this.getTableData();
        },
        methods: {
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        const loading = this.$loading({
                            lock: true,
                            text: '创建中',
                            spinner: 'el-icon-loading',
                            background: 'rgba(0, 0, 0, 0.7)'
                        });
                        this.axios.post('/api/task/save',
                            this.ruleForm
                        ).then(response => {
                            if (response.status == 200) {
                                loading.close();
                                this.getTableData();
                                this.drawer = false;
                                this.$notify({
                                    title: '成功',
                                    message: '创建成功',
                                    type: 'success'
                                });
                            }
                        }).catch(function (error) {
                            console.log(error);
                        });
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            },
            handleEdit(index, row) {
                console.log(index, row);
            },
            handleDelete(index, row) {
                console.log(index, row);
            },
            colorMethod(percentage) {
                if (percentage == 100) {
                    return '#67c23a';
                } else if (percentage >= 75 && percentage < 100) {
                    return '#000000';
                } else if (percentage >= 50 && percentage < 75) {
                    return '#409EFF';
                } else if (percentage >= 25 && percentage < 50) {
                    return '#e6a23c';
                } else if (percentage > 0 && percentage < 25) {
                    return '#909399';
                } else {
                    return '#F56C6C';
                }
            },
            getTableData() {
                let params = {
                    page: this.currentPage,
                    size: this.pageSize,
                    type: -1,
                    name: '',
                    sTime: null,
                    eTime: null,
                }
                // /task/gateway/zuul-task/task/getByRp
                this.axios.get('/api/task/getByRp', {params}).then(response => {
                    this.loading = false;
                    this.tableData = response.data.content;
                    this.total = response.data.total;
                    this.tableData.forEach(function (item, index) {
                        item.activities = [{
                            content: '支持使用图标',
                            timestamp: '2018-04-12 20:46',
                            size: 'large',
                            type: 'primary',
                            icon: 'el-icon-more'
                        }, {
                            content: '支持自定义颜色',
                            timestamp: '2018-04-03 20:46',
                            color: '#0bbd87'
                        }, {
                            content: '支持自定义尺寸',
                            timestamp: '2018-04-03 20:46',
                            size: 'large'
                        }, {
                            content: '默认样式的节点',
                            timestamp: '2018-04-03 20:46'
                        }]
                    });
                }).catch(function (error) {
                    console.log(error);
                });
            },
            handleSizeChange(val) {
                this.pageSize = val;
                this.getTableData();
            },
            handleCurrentChange(val) {
                this.currentPage = val;
                this.getTableData();
            }
        },
    }
</script>

<style scoped>
    /*btn */
    .el-button--mini, .el-button--mini.is-round {
        padding: 2px 6px;
    }

    .el-timeline {
        margin: 0;
        font-size: 12px;
        list-style: none;
    }

    .fieldsetStyle {
        border: 1px solid #DCDFE6;
        background: #FFFFFF;
        margin-bottom: 10px;
    }

    .legendStyle {
        font-size: 12px;
    }
</style>

<style>

</style>
