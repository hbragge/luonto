FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

PR := "${PR}.2"

SRC_URI += "file://fstab \
	file://interfaces.orig \
	file://sshd_config.orig"

do_install_append() {
	install -d ${D}${sysconfdir}/network
	install -m 0644 ${WORKDIR}/interfaces.orig ${D}${sysconfdir}/network/interfaces.orig
	install -m 0644 ${WORKDIR}/fstab ${D}${sysconfdir}/fstab
	install -m 0644 ${WORKDIR}/sshd_config.orig ${D}${sysconfdir}/sshd_config.orig
	mv ${D}${sysconfdir}/hostname ${D}${sysconfdir}/hostname.orig
}
