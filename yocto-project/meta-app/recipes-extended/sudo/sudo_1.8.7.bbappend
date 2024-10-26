PR := "${PR}.1"

do_install_append () {
	echo "ccs     ALL=(ALL) NOPASSWD: ALL" >> ${D}${sysconfdir}/sudoers
	echo "www-data     ALL=(ALL) NOPASSWD: /usr/bin/killall" >> ${D}${sysconfdir}/sudoers
	echo "www-data     ALL=(ALL) NOPASSWD: /sbin/ifconfig" >> ${D}${sysconfdir}/sudoers
}
