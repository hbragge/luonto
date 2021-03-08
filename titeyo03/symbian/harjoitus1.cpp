/* ====================================================================
 * File: harjoitus1.cpp
 * Created: 03/22/06
 * Author: 
 * Copyright (c): , All rights reserved
 * ==================================================================== */

#include "harjoitus1Application.h"

#pragma message("Please ensure that you read the ReadMe file.")

// Create an application, and return a pointer to it
EXPORT_C CApaApplication* NewApplication()
	{
	return new Charjoitus1Application;
	}

// DLL entry point, return that everything is ok
GLDEF_C TInt E32Dll(TDllReason /*aReason*/)
	{
	return KErrNone;
	}
	

