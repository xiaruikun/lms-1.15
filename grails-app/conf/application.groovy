// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'com.next.User'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'com.next.UserRole'
grails.plugin.springsecurity.authority.className = 'com.next.Role'

grails.plugin.springsecurity.controllerAnnotations.staticRules = [[pattern: '/', access: ['permitAll']],
    [pattern: '/error', access: ['permitAll']],
    [pattern: '/index', access: ['permitAll']],
    [pattern: '/index.gsp', access: ['permitAll']],
    [pattern: '/welcome.gsp', access: ['permitAll']],
    [pattern: '/welcome', access: ['permitAll']],
    [pattern: '/notFound', access: ['permitAll']],
    [pattern: '/error', access: ['permitAll']],
    [pattern: '/shutdown', access: ['permitAll']],
    [pattern: '/assets/**', access: ['permitAll']],
    [pattern: '/**/js/**', access: ['permitAll']],
    [pattern: '/**/css/**', access: ['permitAll']],
    [pattern: '/**/images/**', access: ['permitAll']],
    [pattern: '/**/favicon.ico', access: ['permitAll']]]

grails.plugin.springsecurity.filterChain.chainMap = [[pattern: '/assets/**', filters: 'none'],
    [pattern: '/**/js/**', filters: 'none'],
    [pattern: '/**/css/**', filters: 'none'],
    [pattern: '/**/images/**', filters: 'none'],
    [pattern: '/**/favicon.ico', filters: 'none'],
    [pattern: '/**', filters: 'JOINED_FILTERS']]


grails.plugin.springsecurity.successHandler.defaultTargetUrl = '/opportunity/welcome'
grails.plugin.springsecurity.providerNames = ['customAuthenticationProvider',
    'anonymousAuthenticationProvider',
    'rememberMeAuthenticationProvider']

// grails.plugin.springsecurity.failureHandler.defaultFailureUrl='/signIn/authfail?login_error=1'
grails.plugin.springsecurity.adh.errorPage = '/notFound'
// grails.plugin.springsecurity.auth.loginFormUrl='/signIn'
// grails.plugin.springsecurity.successHandler.defaultTargetUrl = '/signIn/index'

// grails.plugin.springsecurity.auth.loginFormUrl='/signIn'

// springsession.allow.persist.mutable = true
grails.plugin.springsecurity.useSecurityEventListener = true
grails.plugin.springsecurity.dao.hideUserNotFoundExceptions = false
