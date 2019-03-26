import org.springframework.cloud.contract.spec.Contract

Contract.make {
	description "should return available dates"

	request {
		method GET()
		url '/v1/report/availabledates'
	}

	response {
		status OK()
		headers {
			contentType applicationJson()
		}
		body (
				['2019-1', '2018-12']
		)
	}
}