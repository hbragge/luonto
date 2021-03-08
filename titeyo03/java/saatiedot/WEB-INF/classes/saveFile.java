static boolean saveFile( String path, StringBuffer sbBuffer, StringBuffer sbError){
			File file = new File(path);
			
			try {
				file.createNewFile();
				} catch(IOException ex) {
				sbError.append("Failed to open file for writing: " + ex);
				return false;
			}
			
			OutputStream os = null;
			try {
				file.createNewFile();
				os = new FileOutputStream(file);
			} catch(Exception ex) {
				sbError.append("Failed to open file for writing: " + ex);
				return false;
			}
			try {
				os.write(sbBuffer.toString().getBytes());
			} catch(Exception ex) {
				sbError.append("Failed to write " + sbBuffer.length() + " byte buffer: " + ex);
				return false;
			} finally {
				try {
					if(os != null) os.close();
				} catch(Exception ex) {
					sbError.append("Failed to close output stream: " + ex);
					return false;
				}
			}
			return true;
		}