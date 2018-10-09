// Place your Spring DSL code here

import com.next.*

beans = {
    loginHistorySecurityEventListener(LoginHistorySecurityEventListener)
    userDetailsService(CustomUserDetailsService)
    preAuthenticationChecks(CustomPreAuthenticationChecks)
    authenticationDetailsSource(CustomWebAuthenticationDetailsSource)

    customAuthenticationProvider(CustomAuthenticationProvider) {
        userDetailsService = ref('userDetailsService')
        passwordEncoder = ref('passwordEncoder')
        userCache = ref('userCache')
        saltSource = ref('saltSource')
        preAuthenticationChecks = ref('preAuthenticationChecks')
        postAuthenticationChecks = ref('postAuthenticationChecks')
        authoritiesMapper = ref('authoritiesMapper')
    }
    // daoAuthenticationProvider(CustomAuthenticationProvider)
}
// beans = {}
