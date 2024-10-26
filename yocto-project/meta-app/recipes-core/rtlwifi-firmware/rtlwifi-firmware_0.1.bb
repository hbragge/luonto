DESCRIPTION = "Firmware for RTLWIFI - driver"
LICENSE = "LGPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=59530bdf33659b29e73d4adb9f9f6552"

inherit localapp

EXTERNALSRC = "${COREBASE}/../drivers/rtl8192su/firmwares"

PR = "r1"

do_compile() {
	echo "Nothing really to do"
}

do_install() {
	install -d ${D}/lib/firmware/rtlwifi
	install -m 0755 ${S}/rtl8712u-oldest-but-good.bin ${D}/lib/firmware/rtlwifi/rtl8712u.bin
}

FILES_${PN} += "/lib/firmware/rtlwifi/rtl8712u.bin"
