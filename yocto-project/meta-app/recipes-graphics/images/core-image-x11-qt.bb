DESCRIPTION = "A very basic X11 image with a terminal and QT libraries"

IMAGE_FEATURES += "splash package-management x11-base qt4-pkgs ssh-server-openssh"

LICENSE = "MIT"

inherit core-image

IMAGE_INSTALL += "icu qtperf4 openssh-sftp"