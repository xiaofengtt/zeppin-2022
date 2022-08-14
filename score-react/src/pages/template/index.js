import React, { Component } from 'react'

import PageContainer from '../../components/PageContainer'
import PageContent from '../../components/PageContent'
import BasicHeader from '../../components/BasicHeader'
import BasicFooter from '../../components/BasicFooter'

class My extends Component {
    render() {
        return (
            <PageContainer>
                <BasicHeader>我的</BasicHeader>
                <PageContent>

                </PageContent>
                <BasicFooter />
            </PageContainer>
        )
    }
}

export default My