package download

import org.springframework.cloud.contract.spec.Contract

Contract.make {
	description "should return detail download data for given time period for all orgs"

	request {
		method GET()
		url '/v1/download/detail/2019/1'
	}

	response {
		status OK()
		headers {
			contentType 'text/csv'
			header(contentDisposition(), "attachment;filename=detail-2019-1.csv")
		}
	}
}