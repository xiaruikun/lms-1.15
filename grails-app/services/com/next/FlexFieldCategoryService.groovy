package com.next

import grails.transaction.Transactional
import groovy.transform.CompileStatic
import groovy.transform.TypeChecked

@Transactional
@CompileStatic
@TypeChecked
class FlexFieldCategoryService
{
    /*
    *初始化弹性域信息
    *author xiaruikun
    */
    def initFlexFieldCategory
    {
        if (!FlexFieldCategory.find("from FlexFieldCategory where name = '抵押物评估值'"))
        {
            println "initFlexFieldCategory"
            def category = new FlexFieldCategory(name: "抵押物评估值")
            category.save()

            def field = new FlexField(name: "地址", description: "地址", displayOrder: 0, dataType: "String", category: category)
            field.save()

            field = new FlexField(name: "所属区县", description: "所属区县", displayOrder: 1, dataType: "String", category: category)
            field.save()

            field = new FlexField(name: "环线(内)", description: "环线(内)", displayOrder: 2, dataType: "String", category: category)
            field.save()

            field = new FlexField(name: "小区别名", description: "小区别名", displayOrder: 3, dataType: "String", category: category)
            field.save()

            field = new FlexField(name: "面积(平米)", description: "面积(平米)", displayOrder: 4, dataType: "Double", defaultValue: "0.00", valueConstraints: '/^[0-9]*[1-9][0-9]*$|^(([1-9]\\d*)(\\.\\d{1,})|(0\\.[1-9]\\d*)|(0\\.\\d*[1-9]))$/', category: category)
            field.save()

            field = new FlexField(name: "建成年代", description: "建成年代", displayOrder: 5, dataType: "Date", category: category)
            field.save()

            field = new FlexField(name: "楼层", description: "楼层", dataType: "Integer", displayOrder: 6, defaultValue: "0", valueConstraints: '/^[0-9]*[1-9][0-9]*$/', category: category)
            field.save()

            field = new FlexField(name: "朝向", description: "朝向", dataType: "String", displayOrder: 7, category: category)
            field.save()

            field = new FlexField(name: "被询公司", description: "被询公司", dataType: "String", displayOrder: 8, category: category)
            field.save()

            field = new FlexField(name: "经纪人姓名", description: "经纪人姓名", dataType: "String", displayOrder: 9, category: category)
            field.save()

            field = new FlexField(name: "经纪人联系方式", description: "经纪人联系方式", dataType: "String", displayOrder: 10, valueConstraints: '/^1\\d{10}$/', category: category)
            field.save()

            field = new FlexField(name: "面积(平米)", description: "面积(平米)", dataType: "Double", displayOrder: 11, defaultValue: "0.00", valueConstraints: '/^[0-9]*[1-9][0-9]*$|^(([1-9]\\d*)(\\.\\d{1,})|(0\\.[1-9]\\d*)|(0\\.\\d*[1-9]))$/', category: category)
            field.save()

            field = new FlexField(name: "单价(万)", description: "单价(万)", dataType: "Double", displayOrder: 12, defaultValue: "0.00", valueConstraints: '/^[0-9]*[1-9][0-9]*$|^(([1-9]\\d*)(\\.\\d{1,})|(0\\.[1-9]\\d*)|(0\\.\\d*[1-9]))$/', category: category)
            field.save()

            field = new FlexField(name: "快速成交价", description: "快速成交价", dataType: "Double", displayOrder: 13, defaultValue: "0.00", valueConstraints: '/^[0-9]*[1-9][0-9]*$|^(([1-9]\\d*)(\\.\\d{1,})|(0\\.[1-9]\\d*)|(0\\.\\d*[1-9]))$/', category: category)
            field.save()

            field = new FlexField(name: "近6个月成交套数", description: "近6个月成交套数", dataType: "Integer", displayOrder: 14, defaultValue: "0", valueConstraints: '/^[0-9]*[1-9][0-9]*$/', category: category)
            field.save()
        }


        if (!FlexFieldCategory.find("from FlexFieldCategory where name = '抵押物其他情况'"))
        {
            def category = new FlexFieldCategory(name: "抵押物其他情况")
            category.save()

            def field = new FlexField(name: "底商、商业体", description: "底商、商业体", displayOrder: 0, dataType: "String", category: category)
            field.save()

            def value = new FlexFieldValue(value: "成熟商业", displayOrder: 0, field: field)
            value.save()

            value = new FlexFieldValue(value: "非成熟商业", displayOrder: 1, field: field)
            value.save()

            value = new FlexFieldValue(value: "无商业", displayOrder: 2, field: field)
            value.save()

            field = new FlexField(name: "交通情况", description: "交通情况", displayOrder: 1, dataType: "String", category: category)
            field.save()

            value = new FlexFieldValue(value: "地铁、公交", displayOrder: 0, field: field)
            value.save()

            value = new FlexFieldValue(value: "公交", displayOrder: 1, field: field)
            value.save()

            value = new FlexFieldValue(value: "无交通", displayOrder: 2, field: field)
            value.save()

            field = new FlexField(name: "医院", description: "医院", displayOrder: 2, dataType: "String", category: category)
            field.save()

            value = new FlexFieldValue(value: "三甲", displayOrder: 0, field: field)
            value.save()

            value = new FlexFieldValue(value: "二类", displayOrder: 1, field: field)
            value.save()

            value = new FlexFieldValue(value: "社区", displayOrder: 2, field: field)
            value.save()

            value = new FlexFieldValue(value: "无", displayOrder: 3, field: field)
            value.save()

            field = new FlexField(name: "学区", description: "学区", displayOrder: 3, dataType: "String", category: category)
            field.save()

            value = new FlexFieldValue(value: "市重点", displayOrder: 0, field: field)
            value.save()

            value = new FlexFieldValue(value: "区重点", displayOrder: 1, field: field)
            value.save()

            value = new FlexFieldValue(value: "普通学区", displayOrder: 2, field: field)
            value.save()

            value = new FlexFieldValue(value: "无", displayOrder: 3, field: field)
            value.save()

            field = new FlexField(name: "学校", description: "学校", displayOrder: 4, dataType: "String", category: category)
            field.save()

            value = new FlexFieldValue(value: "幼儿园", displayOrder: 0, field: field)
            value.save()

            value = new FlexFieldValue(value: "小学", displayOrder: 1, field: field)
            value.save()

            value = new FlexFieldValue(value: "初中", displayOrder: 2, field: field)
            value.save()

            value = new FlexFieldValue(value: "高中", displayOrder: 3, field: field)
            value.save()

            field = new FlexField(name: "学校名称", description: "学校名称", displayOrder: 5, dataType: "String", category: category)
            field.save()

            field = new FlexField(name: "银行", description: "银行", dataType: "String", displayOrder: 6, category: category)
            field.save()

            value = new FlexFieldValue(value: "三家(含)以上", displayOrder: 0, field: field)
            value.save()

            value = new FlexFieldValue(value: "三家以下", displayOrder: 1, field: field)
            value.save()

            value = new FlexFieldValue(value: "无", displayOrder: 2, field: field)
            value.save()

            field = new FlexField(name: "超市", description: "超市", dataType: "String", displayOrder: 7, category: category)
            field.save()

            value = new FlexFieldValue(value: "有", displayOrder: 0, field: field)
            value.save()

            value = new FlexFieldValue(value: "无", displayOrder: 1, field: field)
            value.save()

            field = new FlexField(name: "农贸市场", description: "农贸市场", dataType: "String", displayOrder: 8, category: category)
            field.save()

            value = new FlexFieldValue(value: "有", displayOrder: 0, field: field)
            value.save()

            value = new FlexFieldValue(value: "无", displayOrder: 1, field: field)
            value.save()

            field = new FlexField(name: "经纪公司", description: "经济公司", dataType: "String", displayOrder: 9, category: category)
            field.save()

            value = new FlexFieldValue(value: "三家(含)以上", displayOrder: 0, field: field)
            value.save()

            value = new FlexFieldValue(value: "三家以下", displayOrder: 1, field: field)
            value.save()

            value = new FlexFieldValue(value: "无大型中介", displayOrder: 2, field: field)
            value.save()

            value = new FlexFieldValue(value: "无中介", displayOrder: 3, field: field)
            value.save()

            field = new FlexField(name: "绿化", description: "绿化", dataType: "String", displayOrder: 10, category: category)
            field.save()

            value = new FlexFieldValue(value: "优", displayOrder: 0, field: field)
            value.save()

            value = new FlexFieldValue(value: "良", displayOrder: 1, field: field)
            value.save()

            value = new FlexFieldValue(value: "无", displayOrder: 2, field: field)
            value.save()

            field = new FlexField(name: "停车位", description: "停车位", dataType: "String", displayOrder: 11, category: category)
            field.save()

            value = new FlexFieldValue(value: "地上、地下", displayOrder: 0, field: field)
            value.save()

            value = new FlexFieldValue(value: "仅地上", displayOrder: 1, field: field)
            value.save()

            value = new FlexFieldValue(value: "仅地下", displayOrder: 2, field: field)
            value.save()

            field = new FlexField(name: "楼体情况", description: "楼体情况", dataType: "String", displayOrder: 12, category: category)
            field.save()

            value = new FlexFieldValue(value: "外观新", displayOrder: 0, field: field)
            value.save()

            value = new FlexFieldValue(value: "外观一般", displayOrder: 1, field: field)
            value.save()

            value = new FlexFieldValue(value: "外观旧", displayOrder: 2, field: field)
            value.save()

            field = new FlexField(name: "电梯", description: "电梯", dataType: "String", displayOrder: 13, category: category)
            field.save()

            value = new FlexFieldValue(value: "三部(含)以上", displayOrder: 0, field: field)
            value.save()

            value = new FlexFieldValue(value: "两部", displayOrder: 1, field: field)
            value.save()

            value = new FlexFieldValue(value: "一部", displayOrder: 2, field: field)
            value.save()

            value = new FlexFieldValue(value: "无", displayOrder: 3, field: field)
            value.save()

            field = new FlexField(name: "健身器材", description: "健身器材", dataType: "String", displayOrder: 14, category: category)
            field.save()

            value = new FlexFieldValue(value: "有", displayOrder: 0, field: field)
            value.save()

            value = new FlexFieldValue(value: "无", displayOrder: 1, field: field)
            value.save()

            field = new FlexField(name: "社区规模", description: "社区规模", dataType: "String", displayOrder: 15, category: category)
            field.save()


            value = new FlexFieldValue(value: "10栋以上", displayOrder: 0, field: field)
            value.save()

            value = new FlexFieldValue(value: "6-9栋", displayOrder: 1, field: field)
            value.save()

            value = new FlexFieldValue(value: "4-5栋", displayOrder: 2, field: field)
            value.save()

            value = new FlexFieldValue(value: "1-3栋", displayOrder: 3, field: field)
            value.save()

            field = new FlexField(name: "使用情况", description: "使用情况", dataType: "String", displayOrder: 16, category: category)
            field.save()

            value = new FlexFieldValue(value: "自住", displayOrder: 0, field: field)
            value.save()

            value = new FlexFieldValue(value: "空置", displayOrder: 1, field: field)
            value.save()

            value = new FlexFieldValue(value: "出借", displayOrder: 2, field: field)
            value.save()

            value = new FlexFieldValue(value: "出租", displayOrder: 3, field: field)
            value.save()

            field = new FlexField(name: "装修情况", description: "装修情况", dataType: "String", displayOrder: 17, category: category)
            field.save()

            value = new FlexFieldValue(value: "豪装", displayOrder: 0, field: field)
            value.save()

            value = new FlexFieldValue(value: "精装", displayOrder: 1, field: field)
            value.save()

            value = new FlexFieldValue(value: "简装", displayOrder: 2, field: field)
            value.save()

            value = new FlexFieldValue(value: "毛坯", displayOrder: 3, field: field)
            value.save()
        }

        if (!FlexFieldCategory.find("from FlexFieldCategory where name = '外访预警'"))
        {
            def category = new FlexFieldCategory(name: "外访预警")
            category.save()

            def field = new FlexField(name: "抵押物内有70岁以上老人，行动不便，身体不适", description: "抵押物内有70岁以上老人，行动不便，身体不适", displayOrder: 0, dataType: "String", category: category)
            field.save()

            def value = new FlexFieldValue(value: "有", displayOrder: 0, field: field)
            value.save()

            value = new FlexFieldValue(value: "无", displayOrder: 1, field: field)
            value.save()

            field = new FlexField(name: "抵押物内有限制民事行为能力人活生活无法自理", description: "抵押物内有限制民事行为能力人活生活无法自理", displayOrder: 1, dataType: "String", category: category)
            field.save()

            value = new FlexFieldValue(value: "有", displayOrder: 0, field: field)
            value.save()

            value = new FlexFieldValue(value: "无", displayOrder: 1, field: field)
            value.save()

            field = new FlexField(name: "抵押物内有火灾情况", description: "抵押物内有火灾情况", dataType: "String", displayOrder: 2, category: category)
            field.save()

            value = new FlexFieldValue(value: "有", displayOrder: 0, field: field)
            value.save()

            value = new FlexFieldValue(value: "无", displayOrder: 1, field: field)
            value.save()

            field = new FlexField(name: "抵押物是顶层的有漏水痕迹", description: "抵押物是顶层的有漏水痕迹", displayOrder: 3, dataType: "String", category: category)
            field.save()

            value = new FlexFieldValue(value: "有", displayOrder: 0, field: field)
            value.save()

            value = new FlexFieldValue(value: "无", displayOrder: 1, field: field)
            value.save()

            field = new FlexField(name: "抵押物临街噪音较大(紧邻高速、城铁)", description: "抵押物临街噪音较大(紧邻高速、城铁)", displayOrder: 4, dataType: "String", category: category)
            field.save()

            value = new FlexFieldValue(value: "有", displayOrder: 0, field: field)
            value.save()

            value = new FlexFieldValue(value: "无", displayOrder: 1, field: field)
            value.save()

            field = new FlexField(name: "抵押物内有隔断", description: "抵押物内有隔断", displayOrder: 5, dataType: "String", category: category)
            field.save()

            value = new FlexFieldValue(value: "有", displayOrder: 0, field: field)
            value.save()

            value = new FlexFieldValue(value: "无", displayOrder: 1, field: field)
            value.save()

            field = new FlexField(name: "抵押物打通改变房屋结构", description: "抵押物打通改变房屋结构", displayOrder: 6, dataType: "String", category: category)
            field.save()

            value = new FlexFieldValue(value: "有", displayOrder: 0, field: field)
            value.save()

            value = new FlexFieldValue(value: "无", displayOrder: 1, field: field)
            value.save()

            field = new FlexField(name: "抵押物为筒子楼", description: "抵押物为筒子楼", dataType: "String", displayOrder: 7, category: category)
            field.save()

            value = new FlexFieldValue(value: "有", displayOrder: 0, field: field)
            value.save()

            value = new FlexFieldValue(value: "无", displayOrder: 1, field: field)
            value.save()

            field = new FlexField(name: "抵押物有拆迁情况", description: "抵押物有拆迁情况", displayOrder: 8, dataType: "String", category: category)
            field.save()

            value = new FlexFieldValue(value: "有", displayOrder: 0, field: field)
            value.save()

            value = new FlexFieldValue(value: "无", displayOrder: 1, field: field)
            value.save()

            field = new FlexField(name: "抵押物立项为住宅但实际为办公", description: "抵押物立项为住宅但实际为办公", displayOrder: 9, dataType: "String", category: category)
            field.save()

            value = new FlexFieldValue(value: "有", displayOrder: 0, field: field)
            value.save()

            value = new FlexFieldValue(value: "无", displayOrder: 1, field: field)
            value.save()

            field = new FlexField(name: "抵押物立项为住宅实际也是住宅使用单整栋楼或多数楼层为办公模式", description: "抵押物立项为住宅实际也是住宅使用单整栋楼或多数楼层为办公模式", displayOrder: 10, dataType: "String", category: category)
            field.save()

            value = new FlexFieldValue(value: "有", displayOrder: 0, field: field)
            value.save()

            value = new FlexFieldValue(value: "无", displayOrder: 1, field: field)
            value.save()

            field = new FlexField(name: "抵押物有租户未披露的", description: "抵押物有租户未披露的", displayOrder: 11, dataType: "String", category: category)
            field.save()

            value = new FlexFieldValue(value: "有", displayOrder: 0, field: field)
            value.save()

            value = new FlexFieldValue(value: "无", displayOrder: 1, field: field)
            value.save()

            field = new FlexField(name: "抵押物为半地下、地下室，或紧邻高压线等", description: "抵押物为半地下、地下室，或紧邻高压线等", displayOrder: 12, dataType: "String", category: category)
            field.save()

            value = new FlexFieldValue(value: "有", displayOrder: 0, field: field)
            value.save()

            value = new FlexFieldValue(value: "无", displayOrder: 1, field: field)
            value.save()

            field = new FlexField(name: "抵押物室内有大型动物(危险)", description: "抵押物室内有大型动物(危险)", displayOrder: 13, dataType: "String", category: category)
            field.save()

            value = new FlexFieldValue(value: "有", displayOrder: 0, field: field)
            value.save()

            value = new FlexFieldValue(value: "无", displayOrder: 1, field: field)
            value.save()

            field = new FlexField(name: "借款人婚姻状况存疑的", description: "借款人婚姻状况存疑的", displayOrder: 14, dataType: "String", category: category)
            field.save()

            value = new FlexFieldValue(value: "有", displayOrder: 0, field: field)
            value.save()

            value = new FlexFieldValue(value: "无", displayOrder: 1, field: field)
            value.save()
        }

        if (!FlexFieldCategory.find("from FlexFieldCategory where name = '抵押物情况'"))
        {
            def category = new FlexFieldCategory(name: "抵押物情况")
            category.save()

            def field = new FlexField(name: "抵押物描述", description: "抵押物描述", displayOrder: 1, dataType: "String", category: category)
            field.save()

            field = new FlexField(name: "抵押物权属", description: "抵押物权属", displayOrder: 2, dataType: "String", category: category)
            field.save()

            field = new FlexField(name: "小结", description: "小结", displayOrder: 0, dataType: "String", category: category)
            field.save()
        }

        if (!FlexFieldCategory.find("from FlexFieldCategory where name = '借款人资质小结'"))
        {
            def category = new FlexFieldCategory(name: "借款人资质小结")
            category.save()

            def field = new FlexField(name: "借款人资质小结", description: "借款人资质小结", dataType: "String", category: category)
            field.save()
        }

        if (!FlexFieldCategory.find("from FlexFieldCategory where name = '征信小结'"))
        {
            def category = new FlexFieldCategory(name: "征信小结")
            category.save()

            def field = new FlexField(name: "征信小结", description: "征信小结", dataType: "String", category: category)
            field.save()
        }

        if (!FlexFieldCategory.find("from FlexFieldCategory where name = '大数据小结'"))
        {
            def category = new FlexFieldCategory(name: "大数据小结")
            category.save()

            def field = new FlexField(name: "大数据小结", description: "大数据小结", dataType: "String", category: category)
            field.save()
        }

        if (!FlexFieldCategory.find("from FlexFieldCategory where name = '借款用途'"))
        {
            def category = new FlexFieldCategory(name: "借款用途")
            category.save()

            def field = new FlexField(name: "借款用途", description: "借款用途", dataType: "String", category: category)
            field.save()
        }

        if (!FlexFieldCategory.find("from FlexFieldCategory where name = '还款来源'"))
        {
            def category = new FlexFieldCategory(name: "还款来源")
            category.save()

            def field = new FlexField(name: "还款来源", description: "还款来源", dataType: "String", category: category)
            field.save()
        }

        if (!FlexFieldCategory.find("from FlexFieldCategory where name = '风险结论'"))
        {
            def category = new FlexFieldCategory(name: "风险结论")
            category.save()

            def field = new FlexField(name: "优势", description: "优势", displayOrder: 0, dataType: "String", category: category)
            field.save()

            field = new FlexField(name: "劣势", description: "劣势", displayOrder: 1, dataType: "String", category: category)
            field.save()

            field = new FlexField(name: "授信建议", description: "授信建议", displayOrder: 2, dataType: "String", category: category)
            field.save()
        }

        if (!FlexFieldCategory.find("from FlexFieldCategory where name = '产调结果'"))
        {
            def category = new FlexFieldCategory(name: "产调结果")
            category.save()

            def field = new FlexField(name: "产调地址", description: "产调地址", dataType: "String", category: category)
            field.save()

            field = new FlexField(name: "建委系统是否有此楼栋信息", description: "建委系统是否有此楼栋信息", dataType: "String", category: category)
            field.save()

            def value = new FlexFieldValue(value: "是", displayOrder: 0, field: field)
            value.save()

            value = new FlexFieldValue(value: "否", displayOrder: 1, field: field)
            value.save()

            field = new FlexField(name: "是否有查封", description: "是否有查封", dataType: "String", category: category)
            field.save()

            value = new FlexFieldValue(value: "是", displayOrder: 0, field: field)
            value.save()

            value = new FlexFieldValue(value: "否", displayOrder: 1, field: field)
            value.save()

            field = new FlexField(name: "是否有抵押", description: "是否有抵押", dataType: "String", category: category)
            field.save()

            value = new FlexFieldValue(value: "是", displayOrder: 0, field: field)
            value.save()

            value = new FlexFieldValue(value: "否", displayOrder: 1, field: field)
            value.save()

            field = new FlexField(name: "地址是否有变更", description: "地址是否有变更", dataType: "String", category: category)
            field.save()

            value = new FlexFieldValue(value: "是", displayOrder: 0, field: field)
            value.save()

            value = new FlexFieldValue(value: "否", displayOrder: 1, field: field)
            value.save()

            field = new FlexField(name: "抵押人身份证号是否需要升位", description: "抵押人身份证号是否需要升位", dataType: "String", category: category)
            field.save()

            value = new FlexFieldValue(value: "是", displayOrder: 0, field: field)
            value.save()

            value = new FlexFieldValue(value: "否", displayOrder: 1, field: field)
            value.save()

            field = new FlexField(name: "是否是成本价房", description: "是否是成本价房", dataType: "String", category: category)
            field.save()

            value = new FlexFieldValue(value: "是", displayOrder: 0, field: field)
            value.save()

            value = new FlexFieldValue(value: "否", displayOrder: 1, field: field)
            value.save()

            field = new FlexField(name: "是否被占用", description: "是否被占用", dataType: "String", category: category)
            field.save()

            value = new FlexFieldValue(value: "是", displayOrder: 0, field: field)
            value.save()

            value = new FlexFieldValue(value: "否", displayOrder: 1, field: field)
            value.save()
        }

        if (!FlexFieldCategory.find("from FlexFieldCategory where name = '下户信息'"))
        {
            def category = new FlexFieldCategory(name: "下户信息")
            category.save()

            def field = new FlexField(name: "底商、商业体", description: "底商、商业体", dataType: "String", category: category)
            field.save()

            def value = new FlexFieldValue(value: "成熟商业", displayOrder: 0, field: field)
            value.save()

            value = new FlexFieldValue(value: "非成熟商业", displayOrder: 1, field: field)
            value.save()

            value = new FlexFieldValue(value: "无商业", displayOrder: 2, field: field)
            value.save()

            field = new FlexField(name: "抵押物内有火灾痕迹", description: "抵押物内有火灾痕迹", dataType: "String", category: category)
            field.save()

            value = new FlexFieldValue(value: "有", displayOrder: 0, field: field)
            value.save()

            value = new FlexFieldValue(value: "无", displayOrder: 1, field: field)
            value.save()

            field = new FlexField(name: "抵押物内有70岁及以上老人，行动不便，身体不适", description: "抵押物内有70岁及以上老人，行动不便，身体不适", dataType: "String", category: category)
            field.save()

            value = new FlexFieldValue(value: "有", displayOrder: 0, field: field)
            value.save()

            value = new FlexFieldValue(value: "无", displayOrder: 1, field: field)
            value.save()

            field = new FlexField(name: "装修情况", description: "装修情况", dataType: "String", category: category)
            field.save()

            value = new FlexFieldValue(value: "豪装", displayOrder: 0, field: field)
            value.save()

            value = new FlexFieldValue(value: "精装", displayOrder: 1, field: field)
            value.save()

            value = new FlexFieldValue(value: "简装", displayOrder: 2, field: field)
            value.save()

            value = new FlexFieldValue(value: "毛坯", displayOrder: 3, field: field)
            value.save()

            field = new FlexField(name: "学校", description: "学校", dataType: "String", category: category)
            field.save()

            value = new FlexFieldValue(value: "市重点", displayOrder: 0, field: field)
            value.save()

            value = new FlexFieldValue(value: "区重点", displayOrder: 1, field: field)
            value.save()

            value = new FlexFieldValue(value: "普通学区", displayOrder: 2, field: field)
            value.save()

            value = new FlexFieldValue(value: "高中", displayOrder: 3, field: field)
            value.save()

            value = new FlexFieldValue(value: "初中", displayOrder: 4, field: field)
            value.save()

            value = new FlexFieldValue(value: "小学", displayOrder: 5, field: field)
            value.save()

            value = new FlexFieldValue(value: "幼儿园", displayOrder: 6, field: field)
            value.save()

            value = new FlexFieldValue(value: "无", displayOrder: 7, field: field)
            value.save()

            field = new FlexField(name: "抵押物立项为住宅实际也是住宅使用但整栋楼或多数楼层为办公模式", description: "抵押物立项为住宅实际也是住宅使用但整栋楼或多数楼层为办公模式", dataType: "String", category: category)
            field.save()

            value = new FlexFieldValue(value: "有", displayOrder: 0, field: field)
            value.save()

            value = new FlexFieldValue(value: "无", displayOrder: 1, field: field)
            value.save()
        }

        if (!FlexFieldCategory.find("from FlexFieldCategory where name = '放款前要求'"))
        {
            def category = new FlexFieldCategory(name: "放款前要求")
            category.save()

            def field = new FlexField(name: "放款前要求", description: "放款前要求", dataType: "String", category: category)
            field.save()
        }
    }
}
