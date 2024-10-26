FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

PR := "${PR}.2"

SRC_URI += "file://defconfig \
	file://ifplugd-init \
	file://ifplugd \
	file://ifplugd.action \
	file://ifplugd.conf \
	"

do_install_append() {
	install -d ${D}${sysconfdir}/init.d/
	install -m 0755 ${WORKDIR}/ifplugd-init ${D}${sysconfdir}/init.d/ifplugd
	install -d ${D}${sysconfdir}/default/
	install -m 0755 ${WORKDIR}/ifplugd ${D}${sysconfdir}/default/
	install -d ${D}${sysconfdir}/ifplugd/
	install -m 0755 ${WORKDIR}/ifplugd.action ${D}${sysconfdir}/ifplugd/
	install -m 0755 ${WORKDIR}/ifplugd.conf ${D}${sysconfdir}/ifplugd/
}
