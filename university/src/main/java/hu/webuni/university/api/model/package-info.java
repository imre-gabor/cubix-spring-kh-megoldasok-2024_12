@jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapters({
	@jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter(
			value = io.github.threetenjaxb.core.LocalDateXmlAdapter.class,
			type = java.time.LocalDate.class
	)
})
package hu.webuni.university.api.model;