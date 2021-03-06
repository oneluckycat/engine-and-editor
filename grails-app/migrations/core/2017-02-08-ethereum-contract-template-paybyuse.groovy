package core
databaseChangeLog = {
	changeSet(author: "jtakalai", id: "2017-02-08-ethereum-contract-template-paybyuse") {
		insert(tableName: "module") {
			column(name: "id", valueNumeric: 1100)
			column(name: "version", valueNumeric: 0)
			column(name: "category_id", valueNumeric: 1001) // Ethereum
			column(name: "implementing_class", value: "com.unifina.signalpath.blockchain.templates.PayByUse")
			column(name: "name", value: "PayByUse")
			column(name: "js_module", value: "SolidityModule")
			column(name: "type", value: "module")
			column(name: "module_package_id", valueNumeric: 1)
			column(name: "json_help", value: '{"helpText":"PayByUse Ethereum contract"}')
		}
	}
}