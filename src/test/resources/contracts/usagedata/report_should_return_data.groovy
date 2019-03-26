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
			[
				[
					year: 2019,
					month: 1,
					organizationName: "foobar-org",
					lowerEnvironmentMaxInstances: 1,
					upperEnvironmentMaxInstances: 1,
					upperDetails: [
							[
									platform: "JCC",
									spaceName: "bapbaz-space",
									appName: "quaz-app",
									maxInstances: 1
							]
					],
					lowerDetails: [
							[
									platform: "FOG",
									spaceName: "bapbaz-space",
									appName: "quaz-app",
									maxInstances: 1
							]
					]

				]
			]
		)
	}
}