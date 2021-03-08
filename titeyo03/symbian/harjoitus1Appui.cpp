/* ====================================================================
 * File: harjoitus1AppUi.cpp
 * Created: 03/22/06
 * Author: 
 * Copyright (c):  All rights reserved
 * ==================================================================== */

#include <avkon.hrh>
#include <aknnotewrappers.h> 

#include "harjoitus1.pan"
#include "harjoitus1AppUi.h"
#include "harjoitus1AppView.h"
#include "harjoitus1MyAknView1.h"
#include "harjoitus1.hrh"

// ConstructL is called by the application framework
void Charjoitus1AppUi::ConstructL()
    {
    BaseConstructL();


    iAppView = Charjoitus1AppView::NewL(ClientRect());    
	iView1 = Charjoitus1MyAknView1::NewL();

    AddViewL( iAppView );
    AddViewL( iView1 );

    SetDefaultViewL( *iView1 );
    
    
    AddToStackL(iAppView);
    }

Charjoitus1AppUi::Charjoitus1AppUi()                              
    {
	// no implementation required
    }

Charjoitus1AppUi::~Charjoitus1AppUi()
    {
    if (iAppView)
        {
        RemoveFromStack(iAppView);
        delete iAppView;
        iAppView = NULL;
        }
    }

// handle any menu commands
void Charjoitus1AppUi::HandleCommandL(TInt aCommand)
    {
    switch(aCommand)
        {
        case EEikCmdExit:
        case EAknSoftkeyExit:
            Exit();
            break;

        case Eharjoitus1Command1:
            {
            iAppView->Toggle();
            }
            break;
        case Eharjoitus1Command2:
            {
            iAppView->LoadPosL();
            }
            break;
        case Eharjoitus1Command3:
            {
            iAppView->SavePosL();
            }
            break;
        case Eharjoitus1Command4:
        	{
  			iAppView->ShowSettings();
        	}
  			break;
        case Eharjoitus1SwitchToView1:
        	{
            AppUi()->ActivateLocalViewL(TUid::Uid(Eharjoitus1View1Id));
            }
            break;
        default:
            Panic(Eharjoitus1BasicUi);
            break;
        }
    }



