/* ====================================================================
 * File: harjoitus1Application.cpp
 * Created: 03/22/06
 * Author: 
 * Copyright (c): , All rights reserved
 * ==================================================================== */

#include "harjoitus1Document.h"
#include "harjoitus1Application.h"

// UID for the application, this should correspond to the uid defined in the mmp file
static const TUid KUidharjoitus1App = {0x0B795F77};

CApaDocument* Charjoitus1Application::CreateDocumentL()
    {  
    // Create an harjoitus1 document, and return a pointer to it
    CApaDocument* document = Charjoitus1Document::NewL(*this);
    return document;
    }

TUid Charjoitus1Application::AppDllUid() const
    {
    // Return the UID for the harjoitus1 application
    return KUidharjoitus1App;
    }

