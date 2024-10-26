lib_LTLIBRARIES = libdaemonapp.la

libdaemonapp_la_CPPFLAGS = \
	-I${top_srcdir}/src

libdaemonapp_la_CFLAGS = \
	-Wall \
	-Werror \
	-D_GNU_SOURCE

libdaemonapp_la_SOURCES = \
	daemonapp_api.c

include_HEADERS = \
	daemonapp_api.h

noinst_HEADERS = \
	daemonapp_private.h
