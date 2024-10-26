FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

PR := "${PR}.5"

SRC_URI += "file://create-opt.sh \
	file://urandom \
	file://wlan"

do_install_append() {
	install -d ${D}${sysconfdir}/init.d/
	install -m 0755 ${WORKDIR}/create-opt.sh ${D}${sysconfdir}/init.d/
	install -m 0755 ${WORKDIR}/urandom ${D}${sysconfdir}/init.d/

	install -m 0755 ${WORKDIR}/wlan ${D}${sysconfdir}/init.d/

	# This directory is used during startup for location of startup scripts
	install -d ${D}/var/startup

	# create-opt.sh should run before urandom
	update-rc.d -r ${D} create-opt.sh start 15 S .
}
