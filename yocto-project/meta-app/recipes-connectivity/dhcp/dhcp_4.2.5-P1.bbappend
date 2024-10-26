FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

PR := "${PR}.3"

SRC_URI += "file://dhclient.conf.orig \
	file://dhcpd.conf.orig"


do_install_append() {
	install -m 0644 ${WORKDIR}/dhclient.conf.orig ${D}${sysconfdir}/dhcp/dhclient.conf.orig
	install -m 0644 ${WORKDIR}/dhcpd.conf.orig ${D}${sysconfdir}/dhcp/dhcpd.conf.orig
}

FILES_dhcp-client += "/etc/dhcp/dhclient.conf.orig"
FILES_dhcp-server += "/etc/dhcp/dhcpd.conf.orig"