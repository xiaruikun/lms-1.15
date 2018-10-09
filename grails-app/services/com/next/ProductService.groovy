package com.next

import grails.transaction.Transactional

@Transactional
class ProductService
{
    /*
    *初始化产品信息
    *author xiaruikun
    * */
    def initProduct
    {
        if (!Product.find("from Product where name = '融e贷'"))
        {
            println "init initProduct"
            def product = new Product(name: "融e贷")
            product.save()
        }
        if (!Product.find("from Product where name = '速e贷'"))
        {
            def product = new Product(name: "速e贷")
            product.save()
        }

        if (!Product.find("from Product where name = '等额本息'"))
        {
            def product = new Product(name: "等额本息")
            product.save()
        }

        if (!Product.find("from Product where name = '快贷'"))
        {
            def product = new Product(name: "快贷")
            product.save()
        }

        if (!ProductAccount.find("from ProductAccount where name = '融e贷北京'"))
        {
            if (!Account.find("from Account where name = '中佳信-北京分公司'"))
            {
                def c = new Account(name: "中佳信-北京分公司", type: AccountType.findByName("Partner"), parent: Account.findByName("北京中佳信科技发展有限公司"))
                c.save()
            }

            //融e贷北京
            def productAccount = new ProductAccount(name: "融e贷北京", description: "融e贷北京", maximumAmount: 300, minimumAmount: 10, active: true, account: Account.findByName("中佳信-北京分公司"), principalPaymentMethod: PrincipalPaymentMethod.findByName("月息年本"), product: Product.findByName("融e贷"))
            productAccount.save()

            def pmr = new ProductMortgageRate(mortgageRate: 0.7, assetType: AssetType.findByName("住宅"), product: productAccount)
            pmr.save()

            def pi = new ProductInterest(minimumRate: 0.001, maximumRate: 0.001, monthesOfStart: 24, monthesOfEnd: 36, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("服务费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.001, maximumRate: 0.001, monthesOfStart: 24, monthesOfEnd: 36, contactLevel: ContactLevel.findByName("A"), installments: true, productInterestType: ProductInterestType.findByName("服务费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.0088, maximumRate: 0.0088, monthesOfStart: 24, monthesOfEnd: 36, contactLevel: ContactLevel.findByName("A"), installments: true, productInterestType: ProductInterestType.findByName("基本费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.001, maximumRate: 0.001, monthesOfStart: 24, monthesOfEnd: 36, contactLevel: ContactLevel.findByName("B"), productInterestType: ProductInterestType.findByName("信用调整"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.002, maximumRate: 0.003, monthesOfStart: 24, monthesOfEnd: 36, contactLevel: ContactLevel.findByName("C"), productInterestType: ProductInterestType.findByName("信用调整"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0, maximumRate: 0, monthesOfStart: 24, monthesOfEnd: 36, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("渠道返费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0, maximumRate: 0, monthesOfStart: 24, monthesOfEnd: 36, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("二抵加收费率"), product: productAccount)
            pi.save()
        }
        if (!ProductAccount.find("from ProductAccount where name = '融e贷济南'"))
        {

            if (!Account.find("from Account where name = '中佳信-济南分公司'"))
            {
                def c = new Account(name: "中佳信-济南分公司", type: AccountType.findByName("Partner"), parent: Account.findByName("北京中佳信科技发展有限公司"))
                c.save()
            }

            //融e贷济南
            def productAccount = new ProductAccount(name: "融e贷济南", description: "融e贷济南", maximumAmount: 400, minimumAmount: 10, active: true, account: Account.findByName("中佳信-济南分公司"), principalPaymentMethod: PrincipalPaymentMethod.findByName("月息年本"), product: Product.findByName("融e贷"))
            productAccount.save()

            def pmr = new ProductMortgageRate(mortgageRate: 0.7, assetType: AssetType.findByName("住宅"), product: productAccount)
            pmr.save()

            def pi = new ProductInterest(minimumRate: 0.001, maximumRate: 0.001, monthesOfStart: 36, monthesOfEnd: 36, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("服务费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.001, maximumRate: 0.001, monthesOfStart: 36, monthesOfEnd: 36, contactLevel: ContactLevel.findByName("A"), installments: true, productInterestType: ProductInterestType.findByName("服务费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.0088, maximumRate: 0.0088, monthesOfStart: 36, monthesOfEnd: 36, contactLevel: ContactLevel.findByName("A"), installments: true, productInterestType: ProductInterestType.findByName("基本费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.001, maximumRate: 0.001, monthesOfStart: 36, monthesOfEnd: 36, contactLevel: ContactLevel.findByName("B"), productInterestType: ProductInterestType.findByName("信用调整"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0, maximumRate: 0, monthesOfStart: 36, monthesOfEnd: 36, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("渠道返费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.003, maximumRate: 0.003, monthesOfStart: 36, monthesOfEnd: 36, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("二抵加收费率"), product: productAccount)
            pi.save()
        }
        if (!ProductAccount.find("from ProductAccount where name = '融e贷青岛'"))
        {

            if (!Account.find("from Account where name = '中佳信-青岛分公司'"))
            {
                def c = new Account(name: "中佳信-青岛分公司", type: AccountType.findByName("Partner"), parent: Account.findByName("北京中佳信科技发展有限公司"))
                c.save()
            }

            //融e贷青岛
            def productAccount = new ProductAccount(name: "融e贷青岛", description: "融e贷青岛", maximumAmount: 600, minimumAmount: 10, active: true, account: Account.findByName("中佳信-青岛分公司"), principalPaymentMethod: PrincipalPaymentMethod.findByName("月息年本"), product: Product.findByName("融e贷"))
            productAccount.save()

            def pmr = new ProductMortgageRate(mortgageRate: 0.7, assetType: AssetType.findByName("住宅"), product: productAccount)
            pmr.save()

            def pi = new ProductInterest(minimumRate: 0.001, maximumRate: 0.001, monthesOfStart: 36, monthesOfEnd: 36, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("服务费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.001, maximumRate: 0.001, monthesOfStart: 36, monthesOfEnd: 36, contactLevel: ContactLevel.findByName("A"), installments: true, productInterestType: ProductInterestType.findByName("服务费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.0088, maximumRate: 0.0088, monthesOfStart: 36, monthesOfEnd: 36, contactLevel: ContactLevel.findByName("A"), installments: true, productInterestType: ProductInterestType.findByName("基本费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.001, maximumRate: 0.001, monthesOfStart: 36, monthesOfEnd: 36, contactLevel: ContactLevel.findByName("B"), productInterestType: ProductInterestType.findByName("信用调整"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0, maximumRate: 0, monthesOfStart: 36, monthesOfEnd: 36, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("渠道返费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.003, maximumRate: 0.003, monthesOfStart: 36, monthesOfEnd: 36, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("二抵加收费率"), product: productAccount)
            pi.save()
        }

        if (!ProductAccount.find("from ProductAccount where name = '融e贷成都'"))
        {
            if (!Account.find("from Account where name = '中佳信-成都分公司'"))
            {
                def c = new Account(name: "中佳信-成都分公司", type: AccountType.findByName("Partner"), parent: Account.findByName("北京中佳信科技发展有限公司"))
                c.save()
            }

            //融e贷成都
            def productAccount = new ProductAccount(name: "融e贷成都", description: "融e贷成都", maximumAmount: 400, minimumAmount: 10, active: true, account: Account.findByName("中佳信-成都分公司"), principalPaymentMethod: PrincipalPaymentMethod.findByName("月息年本"), product: Product.findByName("融e贷"))
            productAccount.save()

            def pmr = new ProductMortgageRate(mortgageRate: 0.7, assetType: AssetType.findByName("住宅"), product: productAccount)
            pmr.save()

            def pi = new ProductInterest(minimumRate: 0.001, maximumRate: 0.001, monthesOfStart: 36, monthesOfEnd: 36, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("服务费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.001, maximumRate: 0.001, monthesOfStart: 36, monthesOfEnd: 36, contactLevel: ContactLevel.findByName("A"), installments: true, productInterestType: ProductInterestType.findByName("服务费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.0088, maximumRate: 0.0088, monthesOfStart: 36, monthesOfEnd: 36, contactLevel: ContactLevel.findByName("A"), installments: true, productInterestType: ProductInterestType.findByName("基本费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.001, maximumRate: 0.001, monthesOfStart: 36, monthesOfEnd: 36, contactLevel: ContactLevel.findByName("B"), productInterestType: ProductInterestType.findByName("信用调整"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0, maximumRate: 0, monthesOfStart: 36, monthesOfEnd: 36, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("渠道返费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.003, maximumRate: 0.003, monthesOfStart: 36, monthesOfEnd: 36, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("二抵加收费率"), product: productAccount)
            pi.save()
        }
        if (!ProductAccount.find("from ProductAccount where name = '速e贷北京'"))
        {
            //速e贷北京
            def productAccount = new ProductAccount(name: "速e贷北京", description: "速e贷北京", maximumAmount: 1200, minimumAmount: 20, active: true, account: Account.findByName("中佳信-北京分公司"), principalPaymentMethod: PrincipalPaymentMethod.findByName("到期还本"), product: Product.findByName("速e贷"))
            productAccount.save()

            def pmr = new ProductMortgageRate(mortgageRate: 0.7, assetType: AssetType.findByName("住宅"), product: productAccount)
            pmr.save()

            pmr = new ProductMortgageRate(mortgageRate: 0.65, assetType: AssetType.findByName("商品房"), product: productAccount)
            pmr.save()

            def pi = new ProductInterest(minimumRate: 0.003, maximumRate: 0.003, monthesOfStart: 1, monthesOfEnd: 6, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("服务费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.003, maximumRate: 0.003, monthesOfStart: 1, monthesOfEnd: 6, contactLevel: ContactLevel.findByName("A"), installments: true, productInterestType: ProductInterestType.findByName("服务费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.0088, maximumRate: 0.0088, monthesOfStart: 1, monthesOfEnd: 6, contactLevel: ContactLevel.findByName("A"), installments: true, productInterestType: ProductInterestType.findByName("基本费率"), product: productAccount)
            pi.save()


            pi = new ProductInterest(minimumRate: 0.003, maximumRate: 0.003, monthesOfStart: 7, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("服务费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.003, maximumRate: 0.003, monthesOfStart: 7, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("A"), installments: true, productInterestType: ProductInterestType.findByName("服务费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.0068, maximumRate: 0.0068, monthesOfStart: 7, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("A"), installments: true, productInterestType: ProductInterestType.findByName("基本费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.001, maximumRate: 0.001, monthesOfStart: 1, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("B"), productInterestType: ProductInterestType.findByName("信用调整"), product: productAccount)
            pi.save()


            pi = new ProductInterest(minimumRate: 0.002, maximumRate: 0.003, monthesOfStart: 1, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("C"), productInterestType: ProductInterestType.findByName("信用调整"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0, maximumRate: 0.06, monthesOfStart: 1, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("渠道返费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0, maximumRate: 0.06, monthesOfStart: 1, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("A"), installments: true, productInterestType: ProductInterestType.findByName("渠道返费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0, maximumRate: 0, monthesOfStart: 1, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("二抵加收费率"), product: productAccount)
            pi.save()
        }
        if (!ProductAccount.find("from ProductAccount where name = '速e贷济南'"))
        {
            //速e贷济南
            def productAccount = new ProductAccount(name: "速e贷济南", description: "速e贷济南", maximumAmount: 400, minimumAmount: 10, active: true, account: Account.findByName("中佳信-济南分公司"), principalPaymentMethod: PrincipalPaymentMethod.findByName("到期还本"), product: Product.findByName("速e贷"))
            productAccount.save()

            def pmr = new ProductMortgageRate(mortgageRate: 0.7, assetType: AssetType.findByName("住宅"), product: productAccount)
            pmr.save()

            pmr = new ProductMortgageRate(mortgageRate: 0.65, assetType: AssetType.findByName("商品房"), product: productAccount)
            pmr.save()

            def pi = new ProductInterest(minimumRate: 0.0035, maximumRate: 0.0035, monthesOfStart: 1, monthesOfEnd: 3, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("服务费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.0090, maximumRate: 0.0090, monthesOfStart: 1, monthesOfEnd: 3, contactLevel: ContactLevel.findByName("A"), installments: true, productInterestType: ProductInterestType.findByName("基本费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.003, maximumRate: 0.003, monthesOfStart: 4, monthesOfEnd: 6, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("服务费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.0090, maximumRate: 0.0090, monthesOfStart: 4, monthesOfEnd: 6, contactLevel: ContactLevel.findByName("A"), installments: true, productInterestType: ProductInterestType.findByName("基本费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.0028, maximumRate: 0.0028, monthesOfStart: 7, monthesOfEnd: 9, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("服务费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.0090, maximumRate: 0.0090, monthesOfStart: 7, monthesOfEnd: 9, contactLevel: ContactLevel.findByName("A"), installments: true, productInterestType: ProductInterestType.findByName("基本费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.0025, maximumRate: 0.0025, monthesOfStart: 10, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("服务费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.0090, maximumRate: 0.0090, monthesOfStart: 10, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("A"), installments: true, productInterestType: ProductInterestType.findByName("基本费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.001, maximumRate: 0.001, monthesOfStart: 1, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("B"), productInterestType: ProductInterestType.findByName("信用调整"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.002, maximumRate: 0.003, monthesOfStart: 1, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("C"), productInterestType: ProductInterestType.findByName("信用调整"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0, maximumRate: 0.06, monthesOfStart: 1, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("渠道返费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.003, maximumRate: 0.003, monthesOfStart: 1, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("二抵加收费率"), product: productAccount)
            pi.save()
        }
        if (!ProductAccount.find("from ProductAccount where name = '速e贷青岛'"))
        {
            //速e贷青岛
            def productAccount = new ProductAccount(name: "速e贷青岛", description: "速e贷青岛", maximumAmount: 600, minimumAmount: 10, active: true, account: Account.findByName("中佳信-青岛分公司"), principalPaymentMethod: PrincipalPaymentMethod.findByName("到期还本"), product: Product.findByName("速e贷"))
            productAccount.save()

            def pmr = new ProductMortgageRate(mortgageRate: 0.7, assetType: AssetType.findByName("住宅"), product: productAccount)
            pmr.save()

            pmr = new ProductMortgageRate(mortgageRate: 0.65, assetType: AssetType.findByName("商品房"), product: productAccount)
            pmr.save()

            def pi = new ProductInterest(minimumRate: 0.0035, maximumRate: 0.0035, monthesOfStart: 1, monthesOfEnd: 3, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("服务费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.0090, maximumRate: 0.0090, monthesOfStart: 1, monthesOfEnd: 3, contactLevel: ContactLevel.findByName("A"), installments: true, productInterestType: ProductInterestType.findByName("基本费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.0030, maximumRate: 0.0030, monthesOfStart: 4, monthesOfEnd: 6, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("服务费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.0090, maximumRate: 0.0090, monthesOfStart: 4, monthesOfEnd: 6, contactLevel: ContactLevel.findByName("A"), installments: true, productInterestType: ProductInterestType.findByName("基本费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.0028, maximumRate: 0.0028, monthesOfStart: 7, monthesOfEnd: 9, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("服务费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.0090, maximumRate: 0.0090, monthesOfStart: 7, monthesOfEnd: 9, contactLevel: ContactLevel.findByName("A"), installments: true, productInterestType: ProductInterestType.findByName("基本费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.0025, maximumRate: 0.0025, monthesOfStart: 10, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("服务费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.0090, maximumRate: 0.0090, monthesOfStart: 10, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("A"), installments: true, productInterestType: ProductInterestType.findByName("基本费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.001, maximumRate: 0.001, monthesOfStart: 1, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("B"), productInterestType: ProductInterestType.findByName("信用调整"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.002, maximumRate: 0.003, monthesOfStart: 1, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("C"), productInterestType: ProductInterestType.findByName("信用调整"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0, maximumRate: 0.06, monthesOfStart: 1, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("渠道返费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.003, maximumRate: 0.003, monthesOfStart: 1, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("二抵加收费率"), product: productAccount)
            pi.save()
        }

        if (!ProductAccount.find("from ProductAccount where name = '速e贷成都'"))
        {
            //速e贷成都
            def productAccount = new ProductAccount(name: "速e贷成都", description: "速e贷成都", maximumAmount: 400, minimumAmount: 10, active: true, account: Account.findByName("中佳信-成都分公司"), principalPaymentMethod: PrincipalPaymentMethod.findByName("到期还本"), product: Product.findByName("速e贷"))
            productAccount.save()

            def pmr = new ProductMortgageRate(mortgageRate: 0.7, assetType: AssetType.findByName("住宅"), product: productAccount)
            pmr.save()

            pmr = new ProductMortgageRate(mortgageRate: 0.65, assetType: AssetType.findByName("商品房"), product: productAccount)
            pmr.save()

            def pi = new ProductInterest(minimumRate: 0.0040, maximumRate: 0.0040, monthesOfStart: 1, monthesOfEnd: 3, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("服务费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.0040, maximumRate: 0.0040, monthesOfStart: 1, monthesOfEnd: 3, contactLevel: ContactLevel.findByName("A"), installments: true, productInterestType: ProductInterestType.findByName("服务费费率"), product: productAccount)
            pi.save()


            pi = new ProductInterest(minimumRate: 0.0085, maximumRate: 0.0090, monthesOfStart: 1, monthesOfEnd: 3, contactLevel: ContactLevel.findByName("A"), installments: true, productInterestType: ProductInterestType.findByName("基本费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.0035, maximumRate: 0.0035, monthesOfStart: 4, monthesOfEnd: 6, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("服务费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.0035, maximumRate: 0.0035, monthesOfStart: 4, monthesOfEnd: 6, contactLevel: ContactLevel.findByName("A"), installments: true, productInterestType: ProductInterestType.findByName("服务费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.0085, maximumRate: 0.0085, monthesOfStart: 4, monthesOfEnd: 6, contactLevel: ContactLevel.findByName("A"), installments: true, productInterestType: ProductInterestType.findByName("基本费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.0030, maximumRate: 0.0030, monthesOfStart: 7, monthesOfEnd: 9, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("服务费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.0030, maximumRate: 0.0030, monthesOfStart: 7, monthesOfEnd: 9, contactLevel: ContactLevel.findByName("A"), installments: true, productInterestType: ProductInterestType.findByName("服务费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.0085, maximumRate: 0.0085, monthesOfStart: 7, monthesOfEnd: 9, contactLevel: ContactLevel.findByName("A"), installments: true, productInterestType: ProductInterestType.findByName("基本费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.0025, maximumRate: 0.0025, monthesOfStart: 10, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("服务费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.0025, maximumRate: 0.0025, monthesOfStart: 10, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("A"), installments: true, productInterestType: ProductInterestType.findByName("服务费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.0085, maximumRate: 0.0085, monthesOfStart: 10, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("A"), installments: true, productInterestType: ProductInterestType.findByName("基本费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.001, maximumRate: 0.001, monthesOfStart: 1, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("B"), productInterestType: ProductInterestType.findByName("信用调整"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.002, maximumRate: 0.003, monthesOfStart: 1, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("C"), productInterestType: ProductInterestType.findByName("信用调整"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0, maximumRate: 0.06, monthesOfStart: 1, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("渠道返费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0, maximumRate: 0.06, monthesOfStart: 1, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("A"), installments: true, productInterestType: ProductInterestType.findByName("渠道返费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.003, maximumRate: 0.003, monthesOfStart: 1, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("二抵加收费率"), product: productAccount)
            pi.save()
        }

        if (!ProductAccount.find("from ProductAccount where name = '速e贷合肥'"))
        {
            println "add ProductAccount"
            if (!Account.find("from Account where name = '中佳信-合肥分公司'"))
            {
                def c = new Account(name: "中佳信-合肥分公司", type: AccountType.findByName("Partner"), parent: Account.findByName("北京中佳信科技发展有限公司"))
                c.save()
            }

            //速e贷合肥
            def productAccount = new ProductAccount(name: "速e贷合肥", description: "速e贷合肥", maximumAmount: 400, minimumAmount: 10, active: true, account: Account.findByName("中佳信-合肥分公司"), principalPaymentMethod: PrincipalPaymentMethod.findByName("到期还本"), product: Product.findByName("速e贷"))
            productAccount.save()

            def pmr = new ProductMortgageRate(mortgageRate: 0.7, assetType: AssetType.findByName("住宅"), product: productAccount)
            pmr.save()

            pmr = new ProductMortgageRate(mortgageRate: 0.65, assetType: AssetType.findByName("商品房"), product: productAccount)
            pmr.save()

            def pi = new ProductInterest(minimumRate: 0.0040, maximumRate: 0.0040, monthesOfStart: 1, monthesOfEnd: 3, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("服务费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.0090, maximumRate: 0.0090, monthesOfStart: 1, monthesOfEnd: 3, contactLevel: ContactLevel.findByName("A"), installments: true, productInterestType: ProductInterestType.findByName("基本费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.0035, maximumRate: 0.0035, monthesOfStart: 4, monthesOfEnd: 6, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("服务费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.0090, maximumRate: 0.0090, monthesOfStart: 4, monthesOfEnd: 6, contactLevel: ContactLevel.findByName("A"), installments: true, productInterestType: ProductInterestType.findByName("基本费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.0033, maximumRate: 0.0033, monthesOfStart: 7, monthesOfEnd: 9, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("服务费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.0090, maximumRate: 0.0090, monthesOfStart: 7, monthesOfEnd: 9, contactLevel: ContactLevel.findByName("A"), installments: true, productInterestType: ProductInterestType.findByName("基本费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.0030, maximumRate: 0.0030, monthesOfStart: 10, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("服务费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.0090, maximumRate: 0.0090, monthesOfStart: 10, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("A"), installments: true, productInterestType: ProductInterestType.findByName("基本费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.001, maximumRate: 0.001, monthesOfStart: 1, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("B"), productInterestType: ProductInterestType.findByName("信用调整"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.002, maximumRate: 0.003, monthesOfStart: 1, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("C"), productInterestType: ProductInterestType.findByName("信用调整"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0, maximumRate: 0.06, monthesOfStart: 1, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("渠道返费费率"), product: productAccount)
            pi.save()
        }
        if (!ProductAccount.find("from ProductAccount where name = '速e贷南京'"))
        {
            if (!Account.find("from Account where name = '中佳信-南京分公司'"))
            {
                def c = new Account(name: "中佳信-南京分公司", type: AccountType.findByName("Partner"), parent: Account.findByName("北京中佳信科技发展有限公司"))
                c.save()
            }
            //速e贷南京
            def productAccount = new ProductAccount(name: "速e贷南京", description: "速e贷南京", maximumAmount: 600, minimumAmount: 10, active: true, account: Account.findByName("中佳信-南京分公司"), principalPaymentMethod: PrincipalPaymentMethod.findByName("到期还本"), product: Product.findByName("速e贷"))
            productAccount.save()

            def pmr = new ProductMortgageRate(mortgageRate: 0.7, assetType: AssetType.findByName("住宅"), product: productAccount)
            pmr.save()

            pmr = new ProductMortgageRate(mortgageRate: 0.65, assetType: AssetType.findByName("商品房"), product: productAccount)
            pmr.save()

            def pi = new ProductInterest(minimumRate: 0.0025, maximumRate: 0.0025, monthesOfStart: 1, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("服务费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.0025, maximumRate: 0.0025, monthesOfStart: 1, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("A"), installments: true, productInterestType: ProductInterestType.findByName("服务费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.0090, maximumRate: 0.0090, monthesOfStart: 1, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("A"), installments: true, productInterestType: ProductInterestType.findByName("基本费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.001, maximumRate: 0.001, monthesOfStart: 1, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("B"), productInterestType: ProductInterestType.findByName("信用调整"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.002, maximumRate: 0.003, monthesOfStart: 1, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("C"), productInterestType: ProductInterestType.findByName("信用调整"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0, maximumRate: 0.06, monthesOfStart: 1, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("渠道返费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0, maximumRate: 0.06, monthesOfStart: 1, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("A"), installments: true, productInterestType: ProductInterestType.findByName("渠道返费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.0015, maximumRate: 0.0015, monthesOfStart: 1, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("二抵加收费率"), product: productAccount)
            pi.save()
        }
        if (!ProductAccount.find("from ProductAccount where name = '速e贷上海'"))
        {
            if (!Account.find("from Account where name = '中佳信-上海分公司'"))
            {
                def c = new Account(name: "中佳信-上海分公司", type: AccountType.findByName("Partner"), parent: Account.findByName("北京中佳信科技发展有限公司"))
                c.save()
            }
            //速e贷上海
            def productAccount = new ProductAccount(name: "速e贷上海", description: "速e贷上海", maximumAmount: 1200, minimumAmount: 20, active: true, account: Account.findByName("中佳信-上海分公司"), principalPaymentMethod: PrincipalPaymentMethod.findByName("到期还本"), product: Product.findByName("速e贷"))
            productAccount.save()

            def pmr = new ProductMortgageRate(mortgageRate: 0.7, assetType: AssetType.findByName("住宅"), product: productAccount)
            pmr.save()

            pmr = new ProductMortgageRate(mortgageRate: 0.65, assetType: AssetType.findByName("商品房"), product: productAccount)
            pmr.save()

            def pi = new ProductInterest(minimumRate: 0.0043, maximumRate: 0.0043, monthesOfStart: 1, monthesOfEnd: 5, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("服务费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.0075, maximumRate: 0.0075, monthesOfStart: 1, monthesOfEnd: 5, contactLevel: ContactLevel.findByName("A"), installments: true, productInterestType: ProductInterestType.findByName("基本费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.0033, maximumRate: 0.0033, monthesOfStart: 6, monthesOfEnd: 8, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("服务费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.0075, maximumRate: 0.0075, monthesOfStart: 6, monthesOfEnd: 8, contactLevel: ContactLevel.findByName("A"), installments: true, productInterestType: ProductInterestType.findByName("基本费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.0028, maximumRate: 0.0028, monthesOfStart: 9, monthesOfEnd: 11, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("服务费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.0075, maximumRate: 0.0075, monthesOfStart: 9, monthesOfEnd: 11, contactLevel: ContactLevel.findByName("A"), installments: true, productInterestType: ProductInterestType.findByName("基本费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.0023, maximumRate: 0.0023, monthesOfStart: 12, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("服务费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.0075, maximumRate: 0.0075, monthesOfStart: 12, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("A"), installments: true, productInterestType: ProductInterestType.findByName("基本费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.001, maximumRate: 0.001, monthesOfStart: 1, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("B"), productInterestType: ProductInterestType.findByName("信用调整"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.002, maximumRate: 0.003, monthesOfStart: 1, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("C"), productInterestType: ProductInterestType.findByName("信用调整"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0, maximumRate: 0.06, monthesOfStart: 1, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("渠道返费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.003, maximumRate: 0.003, monthesOfStart: 1, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("二抵加收费率"), product: productAccount)
            pi.save()
        }
        if (!ProductAccount.find("from ProductAccount where name = '速e贷西安'"))
        {

            if (!Account.find("from Account where name = '中佳信-西安分公司'"))
            {
                def c = new Account(name: "中佳信-西安分公司", type: AccountType.findByName("Partner"), parent: Account.findByName("北京中佳信科技发展有限公司"))
                c.save()
            }
            //速e贷西安
            def productAccount = new ProductAccount(name: "速e贷西安", description: "速e贷西安", maximumAmount: 400, minimumAmount: 10, active: true, account: Account.findByName("中佳信-西安分公司"), principalPaymentMethod: PrincipalPaymentMethod.findByName("到期还本"), product: Product.findByName("速e贷"))
            productAccount.save()

            def pmr = new ProductMortgageRate(mortgageRate: 0.7, assetType: AssetType.findByName("住宅"), product: productAccount)
            pmr.save()

            pmr = new ProductMortgageRate(mortgageRate: 0.65, assetType: AssetType.findByName("商品房"), product: productAccount)
            pmr.save()

            def pi = new ProductInterest(minimumRate: 0.0025, maximumRate: 0.0025, monthesOfStart: 1, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("服务费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.01, maximumRate: 0.01, monthesOfStart: 1, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("A"), installments: true, productInterestType: ProductInterestType.findByName("基本费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.001, maximumRate: 0.001, monthesOfStart: 1, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("B"), productInterestType: ProductInterestType.findByName("信用调整"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.002, maximumRate: 0.003, monthesOfStart: 1, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("C"), productInterestType: ProductInterestType.findByName("信用调整"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0, maximumRate: 0.06, monthesOfStart: 1, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("渠道返费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.003, maximumRate: 0.003, monthesOfStart: 1, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("二抵加收费率"), product: productAccount)
            pi.save()
        }
        if (!ProductAccount.find("from ProductAccount where name = '速e贷郑州'"))
        {

            if (!Account.find("from Account where name = '中佳信-郑州分公司'"))
            {
                def c = new Account(name: "中佳信-郑州分公司", type: AccountType.findByName("Partner"), parent: Account.findByName("北京中佳信科技发展有限公司"))
                c.save()
            }
            //速e贷郑州
            def productAccount = new ProductAccount(name: "速e贷郑州", description: "速e贷郑州", maximumAmount: 1200, minimumAmount: 20, active: true, account: Account.findByName("中佳信-郑州分公司"), principalPaymentMethod: PrincipalPaymentMethod.findByName("到期还本"), product: Product.findByName("速e贷"))
            productAccount.save()

            def pmr = new ProductMortgageRate(mortgageRate: 0.7, assetType: AssetType.findByName("住宅"), product: productAccount)
            pmr.save()

            pmr = new ProductMortgageRate(mortgageRate: 0.65, assetType: AssetType.findByName("商品房"), product: productAccount)
            pmr.save()

            def pi = new ProductInterest(minimumRate: 0.0050, maximumRate: 0.0050, monthesOfStart: 1, monthesOfEnd: 3, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("服务费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.0088, maximumRate: 0.0088, monthesOfStart: 1, monthesOfEnd: 3, contactLevel: ContactLevel.findByName("A"), installments: true, productInterestType: ProductInterestType.findByName("基本费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.0040, maximumRate: 0.0040, monthesOfStart: 4, monthesOfEnd: 6, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("服务费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.0088, maximumRate: 0.0088, monthesOfStart: 4, monthesOfEnd: 6, contactLevel: ContactLevel.findByName("A"), installments: true, productInterestType: ProductInterestType.findByName("基本费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.0035, maximumRate: 0.0035, monthesOfStart: 7, monthesOfEnd: 9, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("服务费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.0088, maximumRate: 0.0088, monthesOfStart: 7, monthesOfEnd: 9, contactLevel: ContactLevel.findByName("A"), installments: true, productInterestType: ProductInterestType.findByName("基本费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.0033, maximumRate: 0.0033, monthesOfStart: 10, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("服务费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.0088, maximumRate: 0.0088, monthesOfStart: 10, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("A"), installments: true, productInterestType: ProductInterestType.findByName("基本费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.001, maximumRate: 0.001, monthesOfStart: 1, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("B"), productInterestType: ProductInterestType.findByName("信用调整"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.002, maximumRate: 0.003, monthesOfStart: 1, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("C"), productInterestType: ProductInterestType.findByName("信用调整"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0, maximumRate: 0.06, monthesOfStart: 1, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("渠道返费费率"), product: productAccount)
            pi.save()
        }

        if (!ProductAccount.find("from ProductAccount where name = '速e贷石家庄'"))
        {
            if (!Account.find("from Account where name = '中佳信-石家庄分公司'"))
            {
                def c = new Account(name: "中佳信-石家庄分公司", type: AccountType.findByName("Partner"), parent: Account.findByName("北京中佳信科技发展有限公司"))
                c.save()
            }
            //速e贷石家庄
            def productAccount = new ProductAccount(name: "速e贷石家庄", description: "速e贷石家庄", maximumAmount: 400, minimumAmount: 10, active: true, account: Account.findByName("中佳信-石家庄分公司"), principalPaymentMethod: PrincipalPaymentMethod.findByName("到期还本"), product: Product.findByName("速e贷"))
            productAccount.save()

            def pmr = new ProductMortgageRate(mortgageRate: 0.7, assetType: AssetType.findByName("住宅"), product: productAccount)
            pmr.save()

            def pi = new ProductInterest(minimumRate: 0.004, maximumRate: 0.004, monthesOfStart: 1, monthesOfEnd: 3, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("服务费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.004, maximumRate: 0.004, monthesOfStart: 1, monthesOfEnd: 3, contactLevel: ContactLevel.findByName("A"), installments: true, productInterestType: ProductInterestType.findByName("服务费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.0090, maximumRate: 0.0090, monthesOfStart: 1, monthesOfEnd: 3, contactLevel: ContactLevel.findByName("A"), installments: true, productInterestType: ProductInterestType.findByName("基本费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.003, maximumRate: 0.003, monthesOfStart: 4, monthesOfEnd: 6, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("服务费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.003, maximumRate: 0.003, monthesOfStart: 4, monthesOfEnd: 6, contactLevel: ContactLevel.findByName("A"), installments: true, productInterestType: ProductInterestType.findByName("服务费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.0090, maximumRate: 0.0090, monthesOfStart: 4, monthesOfEnd: 6, contactLevel: ContactLevel.findByName("A"), installments: true, productInterestType: ProductInterestType.findByName("基本费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.002, maximumRate: 0.002, monthesOfStart: 7, monthesOfEnd: 9, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("服务费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.002, maximumRate: 0.002, monthesOfStart: 7, monthesOfEnd: 9, contactLevel: ContactLevel.findByName("A"), installments: true, productInterestType: ProductInterestType.findByName("服务费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.0090, maximumRate: 0.0090, monthesOfStart: 7, monthesOfEnd: 9, contactLevel: ContactLevel.findByName("A"), installments: true, productInterestType: ProductInterestType.findByName("基本费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.002, maximumRate: 0.002, monthesOfStart: 10, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("服务费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.002, maximumRate: 0.002, monthesOfStart: 10, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("A"), installments: true, productInterestType: ProductInterestType.findByName("服务费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.0090, maximumRate: 0.0090, monthesOfStart: 10, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("A"), installments: true, productInterestType: ProductInterestType.findByName("基本费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.001, maximumRate: 0.001, monthesOfStart: 1, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("B"), productInterestType: ProductInterestType.findByName("信用调整"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.002, maximumRate: 0.002, monthesOfStart: 1, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("C"), productInterestType: ProductInterestType.findByName("信用调整"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0, maximumRate: 0.06, monthesOfStart: 1, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("渠道返费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0, maximumRate: 0.06, monthesOfStart: 1, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("A"), installments: true, productInterestType: ProductInterestType.findByName("渠道返费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.001, maximumRate: 0.001, monthesOfStart: 1, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("二抵加收费率"), product: productAccount)
            pi.save()
        }


        if (!ProductAccount.find("from ProductAccount where name = '速e贷厦门'"))
        {
            if (!Account.find("from Account where name = '中佳信-厦门分公司'"))
            {
                def c = new Account(name: "中佳信-厦门分公司", type: AccountType.findByName("Partner"), parent: Account.findByName("北京中佳信科技发展有限公司"))
                c.save()
            }
            //速e贷厦门
            def productAccount = new ProductAccount(name: "速e贷厦门", description: "速e贷厦门", maximumAmount: 600, minimumAmount: 10, active: true, account: Account.findByName("中佳信-厦门分公司"), principalPaymentMethod: PrincipalPaymentMethod.findByName("到期还本"), product: Product.findByName("速e贷"))
            productAccount.save()

            def pmr = new ProductMortgageRate(mortgageRate: 0.7, assetType: AssetType.findByName("住宅"), product: productAccount)
            pmr.save()

            def pi = new ProductInterest(minimumRate: 0.0035, maximumRate: 0.0035, monthesOfStart: 1, monthesOfEnd: 3, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("服务费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.0035, maximumRate: 0.0035, monthesOfStart: 1, monthesOfEnd: 3, contactLevel: ContactLevel.findByName("A"), installments: true, productInterestType: ProductInterestType.findByName("服务费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.0085, maximumRate: 0.0085, monthesOfStart: 1, monthesOfEnd: 3, contactLevel: ContactLevel.findByName("A"), installments: true, productInterestType: ProductInterestType.findByName("基本费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.003, maximumRate: 0.003, monthesOfStart: 4, monthesOfEnd: 6, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("服务费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.003, maximumRate: 0.003, monthesOfStart: 4, monthesOfEnd: 6, contactLevel: ContactLevel.findByName("A"), installments: true, productInterestType: ProductInterestType.findByName("服务费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.0085, maximumRate: 0.0085, monthesOfStart: 4, monthesOfEnd: 6, contactLevel: ContactLevel.findByName("A"), installments: true, productInterestType: ProductInterestType.findByName("基本费率"), product: productAccount)
            pi.save()


            pi = new ProductInterest(minimumRate: 0.0025, maximumRate: 0.0025, monthesOfStart: 7, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("服务费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.0025, maximumRate: 0.0025, monthesOfStart: 7, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("A"), installments: true, productInterestType: ProductInterestType.findByName("服务费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.0085, maximumRate: 0.0085, monthesOfStart: 7, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("A"), installments: true, productInterestType: ProductInterestType.findByName("基本费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.001, maximumRate: 0.001, monthesOfStart: 1, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("B"), productInterestType: ProductInterestType.findByName("信用调整"), product: productAccount)
            pi.save()


            pi = new ProductInterest(minimumRate: 0.002, maximumRate: 0.002, monthesOfStart: 1, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("C"), productInterestType: ProductInterestType.findByName("信用调整"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0, maximumRate: 0.06, monthesOfStart: 1, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("渠道返费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0, maximumRate: 0.06, monthesOfStart: 1, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("A"), installments: true, productInterestType: ProductInterestType.findByName("渠道返费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.002, maximumRate: 0.002, monthesOfStart: 1, monthesOfEnd: 12, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("二抵加收费率"), product: productAccount)
            pi.save()
        }

        if (!ProductAccount.find("from ProductAccount where name = '等额本息北京'"))
        {
            if (!Account.find("from Account where name = '中佳信-北京分公司'"))
            {
                def c = new Account(name: "中佳信-北京分公司", type: AccountType.findByName("Partner"), parent: Account.findByName("北京中佳信科技发展有限公司"))
                c.save()
            }

            //等额本息北京
            def productAccount = new ProductAccount(name: "等额本息北京", description: "等额本息北京", maximumAmount: 1200, minimumAmount: 10, active: true, account: Account.findByName("中佳信-北京分公司"), principalPaymentMethod: PrincipalPaymentMethod.findByName("等额本息"), product: Product.findByName("等额本息"))
            productAccount.save()

            def pmr = new ProductMortgageRate(mortgageRate: 0.8, assetType: AssetType.findByName("住宅"), product: productAccount)
            pmr.save()

            def pi = new ProductInterest(minimumRate: 0.0075, maximumRate: 0.0075, monthesOfStart: 36, monthesOfEnd: 36, installments: true, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("基本费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.0003, maximumRate: 0.0003, monthesOfStart: 36, monthesOfEnd: 36, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("服务费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0, maximumRate: 0.04, monthesOfStart: 36, monthesOfEnd: 36, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("渠道返费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0, maximumRate: 0.002, monthesOfStart: 36, monthesOfEnd: 36, installments: true, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("二抵加收费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0, maximumRate: 0.005, monthesOfStart: 36, monthesOfEnd: 36, installments: true, contactLevel: ContactLevel.findByName("C"), productInterestType: ProductInterestType.findByName("信用调整"), product: productAccount)
            pi.save()
        }

        if (!ProductAccount.find("from ProductAccount where name = '等额本息上海'"))
        {
            if (!Account.find("from Account where name = '中佳信-上海分公司'"))
            {
                def c = new Account(name: "中佳信-上海分公司", type: AccountType.findByName("Partner"), parent: Account.findByName("北京中佳信科技发展有限公司"))
                c.save()
            }

            //等额本息上海
            def productAccount = new ProductAccount(name: "等额本息上海", description: "等额本息上海", maximumAmount: 1200, minimumAmount: 10, active: true, account: Account.findByName("中佳信-上海分公司"), principalPaymentMethod: PrincipalPaymentMethod.findByName("等额本息"), product: Product.findByName("等额本息"))
            productAccount.save()

            def pmr = new ProductMortgageRate(mortgageRate: 0.8, assetType: AssetType.findByName("住宅"), product: productAccount)
            pmr.save()

            def pi = new ProductInterest(minimumRate: 0.0075, maximumRate: 0.0075, monthesOfStart: 36, monthesOfEnd: 36, installments: true, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("基本费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.0003, maximumRate: 0.0003, monthesOfStart: 36, monthesOfEnd: 36, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("服务费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0, maximumRate: 0.04, monthesOfStart: 36, monthesOfEnd: 36, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("渠道返费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0, maximumRate: 0.002, monthesOfStart: 36, monthesOfEnd: 36, installments: true, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("二抵加收费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0, maximumRate: 0.005, monthesOfStart: 36, monthesOfEnd: 36, installments: true, contactLevel: ContactLevel.findByName("C"), productInterestType: ProductInterestType.findByName("信用调整"), product: productAccount)
            pi.save()
        }

        if (!ProductAccount.find("from ProductAccount where name = '等额本息青岛'"))
        {
            if (!Account.find("from Account where name = '中佳信-青岛分公司'"))
            {
                def c = new Account(name: "中佳信-青岛分公司", type: AccountType.findByName("Partner"), parent: Account.findByName("北京中佳信科技发展有限公司"))
                c.save()
            }

            //等额本息青岛
            def productAccount = new ProductAccount(name: "等额本息青岛", description: "等额本息青岛", maximumAmount: 1200, minimumAmount: 10, active: true, account: Account.findByName("中佳信-青岛分公司"), principalPaymentMethod: PrincipalPaymentMethod.findByName("等额本息"), product: Product.findByName("等额本息"))
            productAccount.save()

            def pmr = new ProductMortgageRate(mortgageRate: 0.8, assetType: AssetType.findByName("住宅"), product: productAccount)
            pmr.save()

            def pi = new ProductInterest(minimumRate: 0.007, maximumRate: 0.007, monthesOfStart: 36, monthesOfEnd: 36, installments: true, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("基本费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.0003, maximumRate: 0.0003, monthesOfStart: 36, monthesOfEnd: 36, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("服务费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0, maximumRate: 0.04, monthesOfStart: 36, monthesOfEnd: 36, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("渠道返费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0, maximumRate: 0.002, monthesOfStart: 36, monthesOfEnd: 36, installments: true, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("二抵加收费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0, maximumRate: 0.005, monthesOfStart: 36, monthesOfEnd: 36, installments: true, contactLevel: ContactLevel.findByName("C"), productInterestType: ProductInterestType.findByName("信用调整"), product: productAccount)
            pi.save()
        }

        if (!ProductAccount.find("from ProductAccount where name = '等额本息成都'"))
        {
            if (!Account.find("from Account where name = '中佳信-成都分公司'"))
            {
                def c = new Account(name: "中佳信-成都分公司", type: AccountType.findByName("Partner"), parent: Account.findByName("北京中佳信科技发展有限公司"))
                c.save()
            }

            //等额本息成都
            def productAccount = new ProductAccount(name: "等额本息成都", description: "等额本息成都", maximumAmount: 1200, minimumAmount: 10, active: true, account: Account.findByName("中佳信-成都分公司"), principalPaymentMethod: PrincipalPaymentMethod.findByName("等额本息"), product: Product.findByName("等额本息"))
            productAccount.save()

            def pmr = new ProductMortgageRate(mortgageRate: 0.8, assetType: AssetType.findByName("住宅"), product: productAccount)
            pmr.save()

            def pi = new ProductInterest(minimumRate: 0.007, maximumRate: 0.007, monthesOfStart: 36, monthesOfEnd: 36, installments: true, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("基本费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.0003, maximumRate: 0.0003, monthesOfStart: 36, monthesOfEnd: 36, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("服务费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0, maximumRate: 0.04, monthesOfStart: 36, monthesOfEnd: 36, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("渠道返费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0, maximumRate: 0.002, monthesOfStart: 36, monthesOfEnd: 36, installments: true, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("二抵加收费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0, maximumRate: 0.005, monthesOfStart: 36, monthesOfEnd: 36, installments: true, contactLevel: ContactLevel.findByName("C"), productInterestType: ProductInterestType.findByName("信用调整"), product: productAccount)
            pi.save()
        }

        if (!ProductAccount.find("from ProductAccount where name = '等额本息济南'"))
        {
            if (!Account.find("from Account where name = '中佳信-济南分公司'"))
            {
                def c = new Account(name: "中佳信-济南分公司", type: AccountType.findByName("Partner"), parent: Account.findByName("北京中佳信科技发展有限公司"))
                c.save()
            }

            //等额本息济南
            def productAccount = new ProductAccount(name: "等额本息济南", description: "等额本息济南", maximumAmount: 1200, minimumAmount: 10, active: true, account: Account.findByName("中佳信-济南分公司"), principalPaymentMethod: PrincipalPaymentMethod.findByName("等额本息"), product: Product.findByName("等额本息"))
            productAccount.save()

            def pmr = new ProductMortgageRate(mortgageRate: 0.8, assetType: AssetType.findByName("住宅"), product: productAccount)
            pmr.save()

            def pi = new ProductInterest(minimumRate: 0.007, maximumRate: 0.007, monthesOfStart: 36, monthesOfEnd: 36, installments: true, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("基本费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.0003, maximumRate: 0.0003, monthesOfStart: 36, monthesOfEnd: 36, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("服务费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0, maximumRate: 0.04, monthesOfStart: 36, monthesOfEnd: 36, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("渠道返费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0, maximumRate: 0.002, monthesOfStart: 36, monthesOfEnd: 36, installments: true, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("二抵加收费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0, maximumRate: 0.005, monthesOfStart: 36, monthesOfEnd: 36, installments: true, contactLevel: ContactLevel.findByName("C"), productInterestType: ProductInterestType.findByName("信用调整"), product: productAccount)
            pi.save()
        }

        if (!ProductAccount.find("from ProductAccount where name = '等额本息合肥'"))
        {
            if (!Account.find("from Account where name = '中佳信-合肥分公司'"))
            {
                def c = new Account(name: "中佳信-合肥分公司", type: AccountType.findByName("Partner"), parent: Account.findByName("北京中佳信科技发展有限公司"))
                c.save()
            }

            //等额本息合肥
            def productAccount = new ProductAccount(name: "等额本息合肥", description: "等额本息合肥", maximumAmount: 1200, minimumAmount: 10, active: true, account: Account.findByName("中佳信-合肥分公司"), principalPaymentMethod: PrincipalPaymentMethod.findByName("等额本息"), product: Product.findByName("等额本息"))
            productAccount.save()

            def pmr = new ProductMortgageRate(mortgageRate: 0.8, assetType: AssetType.findByName("住宅"), product: productAccount)
            pmr.save()

            def pi = new ProductInterest(minimumRate: 0.007, maximumRate: 0.007, monthesOfStart: 36, monthesOfEnd: 36, installments: true, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("基本费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0.0003, maximumRate: 0.0003, monthesOfStart: 36, monthesOfEnd: 36, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("服务费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0, maximumRate: 0.04, monthesOfStart: 36, monthesOfEnd: 36, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("渠道返费费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0, maximumRate: 0.002, monthesOfStart: 36, monthesOfEnd: 36, installments: true, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("二抵加收费率"), product: productAccount)
            pi.save()

            pi = new ProductInterest(minimumRate: 0, maximumRate: 0.005, monthesOfStart: 36, monthesOfEnd: 36, installments: true, contactLevel: ContactLevel.findByName("C"), productInterestType: ProductInterestType.findByName("信用调整"), product: productAccount)
            pi.save()
        }

        if (!ProductAccount.find("from ProductAccount where name = '快贷北京'"))
        {
            if (!Account.find("from Account where name = '中佳信-北京分公司'"))
            {
                def c = new Account(name: "中佳信-北京分公司", type: AccountType.findByName("Partner"), parent: Account.findByName("北京中佳信科技发展有限公司"))
                c.save()
            }

            //快贷北京
            def productAccount = new ProductAccount(name: "快贷北京", description: "快贷北京", maximumAmount: 800, minimumAmount: 0, active: true, account: Account.findByName("中佳信-北京分公司"), principalPaymentMethod: PrincipalPaymentMethod.findByName("到期还本"), product: Product.findByName("快贷"))
            productAccount.save()

            def pmr = new ProductMortgageRate(mortgageRate: 0.7, assetType: AssetType.findByName("住宅"), product: productAccount)
            pmr.save()

            def pi = new ProductInterest(minimumRate: 0.85, maximumRate: 0.85, monthesOfStart: 1, monthesOfEnd: 12, installments: true, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("基本费率"), product: productAccount)
            pi.save()
        }

        if (!ProductAccount.find("from ProductAccount where name = '快贷上海'"))
        {
            if (!Account.find("from Account where name = '中佳信-上海分公司'"))
            {
                def c = new Account(name: "中佳信-上海分公司", type: AccountType.findByName("Partner"), parent: Account.findByName("北京中佳信科技发展有限公司"))
                c.save()
            }

            //快贷上海
            def productAccount = new ProductAccount(name: "快贷上海", description: "快贷上海", maximumAmount: 800, minimumAmount: 0, active: true, account: Account.findByName("中佳信-上海分公司"), principalPaymentMethod: PrincipalPaymentMethod.findByName("到期还本"), product: Product.findByName("快贷"))
            productAccount.save()

            def pmr = new ProductMortgageRate(mortgageRate: 0.7, assetType: AssetType.findByName("住宅"), product: productAccount)
            pmr.save()

            def pi = new ProductInterest(minimumRate: 0.85, maximumRate: 0.85, monthesOfStart: 1, monthesOfEnd: 12, installments: true, contactLevel: ContactLevel.findByName("A"), productInterestType: ProductInterestType.findByName("基本费率"), product: productAccount)
            pi.save()
        }
    }

    /*
    *校验产品信息
    *author xiaruikun
    *20170331
    * */

    def verifyInterest(OpportunityProduct opportunityProduct)
    {
        BigDecimal b1 = new BigDecimal(Double.toString(opportunityProduct.rate));
        BigDecimal b2 = new BigDecimal(Double.toString(100.00));
        def interest = b1.divide(b2, 10, BigDecimal.ROUND_HALF_UP).doubleValue()
        def productInterest
        if (opportunityProduct?.productInterestType?.name == "信用调整")
        {
            if (opportunityProduct?.opportunity?.product?.name == '等额本息'){
                if (opportunityProduct.firstPayMonthes == 0){
                    productInterest = ProductInterest.findAll("from ProductInterest where contactLevel.id=${opportunityProduct?.opportunity?.lender?.level?.id} and productInterestType.id=${opportunityProduct?.productInterestType?.id} and installments=${opportunityProduct.installments} and minimumRate<=${interest} and  monthesOfStart<=${opportunityProduct.opportunity.actualLoanDuration} and monthesOfEnd>=${opportunityProduct.opportunity.actualLoanDuration} and firstPayMonthes =${opportunityProduct.firstPayMonthes} and product.id=${opportunityProduct?.opportunity?.productAccount?.id}")
                }
            }else if (opportunityProduct?.opportunity?.product?.name == '速e贷'){
                if (opportunityProduct?.opportunity?.interestPaymentMethod?.name == "下扣息"||(opportunityProduct?.installments==true && opportunityProduct?.firstPayMonthes ==1)||(opportunityProduct?.installments==false && opportunityProduct?.firstPayMonthes == 0)){
                    productInterest = ProductInterest.findAll("from ProductInterest where contactLevel.id=${opportunityProduct?.opportunity?.lender?.level?.id} and productInterestType.id=${opportunityProduct?.productInterestType?.id} and installments=${opportunityProduct.installments} and minimumRate<=${interest} and  monthesOfStart<=${opportunityProduct.opportunity.actualLoanDuration} and monthesOfEnd>=${opportunityProduct.opportunity.actualLoanDuration} and firstPayMonthes<=${opportunityProduct.firstPayMonthes} and product.id=${opportunityProduct?.opportunity?.productAccount?.id}")
                }
            }else {
                productInterest = ProductInterest.findAll("from ProductInterest where contactLevel.id=${opportunityProduct?.opportunity?.lender?.level?.id} and productInterestType.id=${opportunityProduct?.productInterestType?.id} and installments=${opportunityProduct.installments} and minimumRate<=${interest} and  monthesOfStart<=${opportunityProduct.opportunity.actualLoanDuration} and monthesOfEnd>=${opportunityProduct.opportunity.actualLoanDuration} and firstPayMonthes<=${opportunityProduct.firstPayMonthes} and product.id=${opportunityProduct?.opportunity?.productAccount?.id}")
            }
        }
        else
        {
            if (opportunityProduct?.opportunity?.product?.name == '等额本息'){
                println opportunityProduct.firstPayMonthes
                if (opportunityProduct.firstPayMonthes == 0){
                    productInterest = ProductInterest.findAll("from ProductInterest where productInterestType.id=${opportunityProduct?.productInterestType?.id} and installments=${opportunityProduct.installments} and minimumRate<=${interest} and  monthesOfStart<=${opportunityProduct.opportunity.actualLoanDuration} and monthesOfEnd>=${opportunityProduct.opportunity.actualLoanDuration} and firstPayMonthes<=${opportunityProduct.firstPayMonthes} and product.id=${opportunityProduct?.opportunity?.productAccount?.id}")
                }
            }else if (opportunityProduct?.opportunity?.product?.name == '速e贷'){
                if (opportunityProduct?.opportunity?.interestPaymentMethod?.name == "下扣息"||(opportunityProduct?.installments==true && opportunityProduct?.firstPayMonthes ==1)||(opportunityProduct?.installments==false && opportunityProduct?.firstPayMonthes == 0)){
                    productInterest = ProductInterest.findAll("from ProductInterest where productInterestType.id=${opportunityProduct?.productInterestType?.id} and installments=${opportunityProduct.installments} and minimumRate<=${interest} and  monthesOfStart<=${opportunityProduct.opportunity.actualLoanDuration} and monthesOfEnd>=${opportunityProduct.opportunity.actualLoanDuration} and firstPayMonthes<=${opportunityProduct.firstPayMonthes} and product.id=${opportunityProduct?.opportunity?.productAccount?.id}")
                }
            }else {
                productInterest = ProductInterest.findAll("from ProductInterest where productInterestType.id=${opportunityProduct?.productInterestType?.id} and installments=${opportunityProduct.installments} and minimumRate<=${interest} and  monthesOfStart<=${opportunityProduct.opportunity.actualLoanDuration} and monthesOfEnd>=${opportunityProduct.opportunity.actualLoanDuration} and firstPayMonthes<=${opportunityProduct.firstPayMonthes} and product.id=${opportunityProduct?.opportunity?.productAccount?.id}")
            }
        }
        if (productInterest && productInterest.size() > 0)
        {
            return true
        }
        else
        {
            return false
        }
    }
}
