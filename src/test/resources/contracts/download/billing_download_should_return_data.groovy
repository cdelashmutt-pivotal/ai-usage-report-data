package download

import org.springframework.cloud.contract.spec.Contract

Contract.make {
	description "should return billing download data for given time period for all orgs"

	request {
		method GET()
		url '/v1/download/billing/2019/1'
	}

	response {
		status OK()
		headers {
			contentType 'text/csv'
			header(contentDisposition(), "attachment;filename=billing-2019-1.csv")
		}
		body '''\
Org,Lower Max Concurrent,Upper Max Concurrent,Total Max Concurrent
bu-1,5,10,15
bu-2,2,2,4
bu-3,4,3,7'''
	}
}