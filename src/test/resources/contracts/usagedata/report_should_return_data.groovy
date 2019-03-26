import org.springframework.cloud.contract.spec.Contract

Contract.make {
	description "should return summary data and details for given time period for all orgs"

	request {
		method GET()
		url '/v1/report/2019/1'
	}

	response {
		status OK()
		headers {
			contentType applicationJson()
		}
		body (
			summary: [
				[
					year: 2019,
					month: 1,
					platform: "FOG",
					organizationGUID: "3c157d6c-675e-45f4-978d-aaad1a3980f3",
					organizationName: "foobar-org",
					maxInstances: 1,
					maxTasks: 0
				]
			],
			details: [
				[
					year: 2019,
					month: 1,
					platform: "FOG",
					organizationGUID: "3c157d6c-675e-45f4-978d-aaad1a3980f3",
					organizationName: "foobar-org",
					spaceGUID: "625af3db-a61f-47bd-8a52-88a0e909a691",
					spaceName: "bapbaz-space",
					appGUID: "004c9a54-8b7d-477e-acc1-85cef334f5cf",
					appName: "quaz-app",
					maxInstances: 1,
					maxTasks: 0,
					memoryInMBPerInstance: 2,
					durationInSeconds: 2592000
				]
			]
		)
	}
}