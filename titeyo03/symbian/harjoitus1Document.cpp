/* ====================================================================
 * File: harjoitus1Document.cpp
 * Created: 03/22/06
 * Author: 
 * Copyright (c): , All rights reserved
 * ==================================================================== */

#include "harjoitus1AppUi.h"
#include "harjoitus1Document.h"

// Standard Symbian OS construction sequence
Charjoitus1Document* Charjoitus1Document::NewL(CEikApplication& aApp)
    {
    Charjoitus1Document* self = NewLC(aApp);
    CleanupStack::Pop(self);
    return self;
    }

Charjoitus1Document* Charjoitus1Document::NewLC(CEikApplication& aApp)
    {
    Charjoitus1Document* self = new (ELeave) Charjoitus1Document(aApp);
    CleanupStack::PushL(self);
    self->ConstructL();
    return self;
    }

void Charjoitus1Document::ConstructL()
    {
	// no implementation required
    }    

Charjoitus1Document::Charjoitus1Document(CEikApplication& aApp) : CAknDocument(aApp) 
    {
	// no implementation required
    }

Charjoitus1Document::~Charjoitus1Document()
    {
	// no implementation required
    }

CEikAppUi* Charjoitus1Document::CreateAppUiL()
    {
    // Create the application user interface, and return a pointer to it,
    // the framework takes ownership of this object
    CEikAppUi* appUi = new (ELeave) Charjoitus1AppUi;
    return appUi;
    }

