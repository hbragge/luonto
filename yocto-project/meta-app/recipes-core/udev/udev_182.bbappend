FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

PR := "${PR}.1"

SRC_URI += "file://11-media-by-label-auto-mount.rules"

do_install_append() {
	sed -i 's/="\/etc/="\/opt\/etc/' ${D}${sysconfdir}/default/udev-cache
	sed -i 's/\/etc\/udev\/cache.data/\/opt\/etc\/udev\/cache.data/' ${D}${sysconfdir}/init.d/udev
	sed -i 's/\/etc\/udev\/cache.data/\/opt\/etc\/udev\/cache.data/' ${D}${sysconfdir}/init.d/udev-cache
	install -m 0644 ${WORKDIR}/11-media-by-label-auto-mount.rules ${D}${sysconfdir}/udev/rules.d/
}
