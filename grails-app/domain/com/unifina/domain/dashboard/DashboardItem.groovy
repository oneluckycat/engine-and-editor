package com.unifina.domain.dashboard

import com.unifina.domain.signalpath.Canvas
import com.unifina.utils.IdGenerator

class DashboardItem implements Comparable {

	String id
	String title
	Canvas canvas
	Integer module
	String webcomponent

	static belongsTo = [dashboard: Dashboard, canvas: Canvas]

	static constraints = {
		title(nullable: true)
	}

	int compareTo(obj) {
		return id != null ? id.compareTo(obj.id) : title.compareTo(obj.title)
	}

	static mapping = {
		id generator: 'assigned'
	}

	Map toMap() {
		return [
				id          : id,
				dashboard   : dashboard.id,
				title       : title,
				canvas      : canvas.id,
				module      : module,
				webcomponent: webcomponent
		]
	}

	void updateWebcomponent() {
		def module = canvas.toMap().modules.find { it.hash == module }
		webcomponent = module?.uiChannel?.webcomponent
	}
}
