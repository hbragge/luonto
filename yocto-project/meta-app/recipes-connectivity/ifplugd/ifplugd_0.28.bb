SUMMARY = "Configuration daemon for ethernet devices"
DESCRIPTION = "Configuration daemon for ethernet devices"
HOMEPAGE = "http://0pointer.de/lennart/projects/ifplugd/"
SECTION = "console/network"

LICENSE = "GPLv3+"
LIC_FILES_CHKSUM = "file://LICENSE;md5=94d55d512a9ba36caa9b7df079bae19f"

PR = "r2"

SRC_URI = "http://0pointer.de/lennart/projects/ifplugd/${BPN}-${PV}.tar.gz"

SRC_URI[md5sum] = "df6f4bab52f46ffd6eb1f5912d4ccee3"
SRC_URI[sha256sum] = "474754ac4ab32d738cbf2a4a3e87ee0a2c71b9048a38bdcd7df1e4f9fd6541f0"

inherit autotools

EXTRA_OECONF = "--disable-lynx"

