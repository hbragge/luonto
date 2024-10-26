# create install dir
mkdir $HOME/install_dir

# build and install daemonlib
cd daemonlib
autoreconf --install
./configure  --prefix=$HOME/install_dir
make
make install
cd ..

# build and install daemonapp
cd daemonapp
autoreconf --install
export LIBRARY_PATH=$HOME/install_dir/lib
export C_INCLUDE_PATH=$HOME/install_dir/include
./configure  --prefix=$HOME/install_dir
make
make install

# test
$HOME/install_dir/bin/daemonapp_tool
