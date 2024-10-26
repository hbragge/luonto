dnl project
AC_PREREQ([2.61])
AC_INIT([daemonapp], 1.0, [some@email.com]) 
AM_INIT_AUTOMAKE 

dnl source
AC_CONFIG_SRCDIR([src/daemonapp_api.c])
AC_CONFIG_HEADERS([config.h])

dnl tools
AC_PROG_CC
AC_PROG_INSTALL
AC_PROG_CC_C_O
AC_PROG_LIBTOOL
AM_PROG_CC_C_O

AC_CHECK_LIB([daemonlib], [daemonlib_init],, 
  echo "------------------------------------------"
  echo "need daemonlib implementation"
  echo "------------------------------------------"
  (exit 1); exit 1;

)


dnl Makefiles to generate
AC_CONFIG_FILES([Makefile
		 tool/Makefile
                 src/Makefile])
dnl run
AC_OUTPUT
