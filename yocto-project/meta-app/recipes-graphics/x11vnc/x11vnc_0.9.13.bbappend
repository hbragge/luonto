FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

PR := "${PR}.1"

SRC_URI += "file://x11vnc"

do_install_append() {
	install -d ${D}${sysconfdir}/init.d/
	install -m 0755 ${WORKDIR}/x11vnc ${D}${sysconfdir}/init.d/

	update-rc.d -r ${D} x11vnc start 95 S . stop 05 1 6 .
}