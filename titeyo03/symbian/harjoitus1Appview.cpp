/* ====================================================================
 * File: harjoitus1AppView.cpp
 * Created: 03/22/06
 * Author: 
 * Copyright (c): , All rights reserved
 * ==================================================================== */

#include <coemain.h>
#include <harjoitus1.rsg>
#include <s32file.h>
#include <s32strm.h>
#include <aknnotewrappers.h>
#include <eikenv.h>

#include "harjoitus1Appview.h"
#include "harjoitus1MyForm.h"
#include "harjoitus1.hrh"

TKeyResponse Charjoitus1AppView::OfferKeyEventL(const TKeyEvent& aKeyEvent, TEventCode /*aType*/)
	{
	Toggle();
	
	TInt maxX = Rect().iBr.iX-10;
    TInt maxY = Rect().iTl.iY; 
    TInt minX = Rect().iTl.iX;
    TInt minY = Rect().iBr.iY-10;
    
	if (aKeyEvent.iCode == EKeyLeftArrow)
	{
	x = x-10;
	if (x<minX)
		x = minX;
	DrawNow();
	}
	if (aKeyEvent.iCode == EKeyRightArrow)
	{
	x = x+10;
	if (x>maxX)
		x = maxX;
	DrawNow();
	}
	if (aKeyEvent.iCode == EKeyUpArrow)
	{
	y = y-10;
	if (y<maxY)
		y = maxY;
	DrawNow();
	}
	if (aKeyEvent.iCode == EKeyDownArrow)
	{
	y = y+10;
	if (y>minY)
		y = minY;
	DrawNow();
	}
	return EKeyWasConsumed;
	}
	
void Charjoitus1AppView::Toggle()
	{
	iFillGray = !iFillGray;	
	DrawNow();
	}
	

// Standard construction sequence
Charjoitus1AppView* Charjoitus1AppView::NewL(const TRect& aRect)
    {
    Charjoitus1AppView* self = Charjoitus1AppView::NewLC(aRect);
    CleanupStack::Pop(self);
    return self;
    }

Charjoitus1AppView* Charjoitus1AppView::NewLC(const TRect& aRect)
    {
    Charjoitus1AppView* self = new (ELeave) Charjoitus1AppView;
    CleanupStack::PushL(self);
    self->ConstructL(aRect);
    return self;
    }

Charjoitus1AppView::Charjoitus1AppView()
    {
	// no implementation required
    }

Charjoitus1AppView::~Charjoitus1AppView()
    {
	// no implementation required
    }

void Charjoitus1AppView::ConstructL(const TRect& aRect)
    {
    // Create a window for this application view
    CreateWindowL();

    // Set the windows size
    SetRect(aRect);

    // Activate the window, which makes it ready to be drawn
    ActivateL();
    }

void Charjoitus1AppView::InternalizeL( RReadStream& aStream )
	{
	x = aStream.ReadInt32L();
	y = aStream.ReadInt32L();
	}

void Charjoitus1AppView::ExternalizeL( RWriteStream& aStream ) const
	{
	aStream.WriteInt32L(x);
	aStream.WriteInt32L(y);
	}

void Charjoitus1AppView::LoadPosL()
	{
	RFs session = CCoeEnv::Static()->FsSession();
	RFileReadStream stream;
	stream.PushL();//Siivouspinoon.
	_LIT(KFile,"C:\\pallo.dat");
	TInt err = stream.Open(session,KFile,EFileRead);
	
	if (err != KErrNone)
	  	{
		  CAknInformationNote*  note = new CAknInformationNote;
		  note->ExecuteLD(_L("Tietoja ei ole"));
	  	}
	else
	  	{
		  stream >> *this; //Tai vaihtoehtoisesti ExternalizeL(stream);
		  stream.Close();
		  
  		}
CleanupStack::PopAndDestroy();
	}

void Charjoitus1AppView::SavePosL()
	{
	RFs session = CCoeEnv::Static()->FsSession();
	RFileWriteStream stream;
	stream.PushL();//Siivouspinoon.
	_LIT(KFile,"C:\\pallo.dat");
	TInt err = stream.Open(session,KFile,EFileWrite);
	if (err != KErrNone)
	  	{
		  //Avaus ei onnistunut, koitetaan luoda uusi...
		  err = stream.Create(session,KFile,EFileWrite);
	  	}
	if (err != KErrNone)
	  	{
		  CAknInformationNote*  note = new CAknInformationNote;
		  note->ExecuteLD(_L("Cannot save"));
	  	}
	else
	  	{
		  stream << *this; //Tai vaihtoehtoisesti ExternalizeL(stream);
		  stream.Close();
  		}

CleanupStack::PopAndDestroy();
	}
	
// Settings-form

void Charjoitus1AppView::SetBallPosition( TInt aX, TInt aY ) //Asettaa pallon paikan parametrien mukaan'
	{
	x = aX;
	y = aY;
	}
	
void Charjoitus1AppView::BallPosition( TInt& aX, TInt& aY ) //Kirjoittaa pallon koordinaatit aX/aY:hyn
	{
	aX = x;
	aY = y;
	}
	
void Charjoitus1AppView::MaxValues( TInt& aMaxX, TInt& aMaxY ) //Kirjoittaa pallon koordinaattien max-arvot aMaxX/aMaxY:hyn
	{
    aMaxX = Rect().iBr.iX-10;
    aMaxY = Rect().iBr.iY;
	}	
	
void Charjoitus1AppView::ShowSettings() // Esittää lomakkeen
	{
		Charjoitus1MyForm* form = Charjoitus1MyForm::NewL(this);
		form->ExecuteLD(R_MY_FORM_DIALOG);
	}

// View-vaihto

/*TUid Charjoitus1AppView::Id() const
    {
    return TUid::Uid(ESampleView1Id);
    }*/
    
//void Charjoitus1AppView::DoActivateL(const TVwsViewId& /*aPrevViewId*/,
//                                    TUid /*aCustomMessageId*/,
//                                    const TDesC8& /*aCustomMessage*/)
/*    {
    ASSERT(!(iContainer));
    iContainer = Charjoitus1View1Container::NewL( ClientRect() );
	AppUi()->AddToStackL(iContainer);
    }

void Charjoitus1AppView::DoDeactivate()
    {
    if (iContainer)
        {
        AppUi()->RemoveFromStack(iContainer);
        delete iContainer;
        iContainer = NULL;
        }

    }*/



// Draw this application's view to the screen
void Charjoitus1AppView::Draw(const TRect& /*aRect*/) const
    {
    // Get the standard graphics context 
    CWindowGc& gc = SystemGc();
    
    
    
    if (iFillGray)
    {
    gc.SetBrushColor(KRgbGray); 
    }
    else gc.SetBrushColor(KRgbGreen);
    
    TRect rect = Rect();
    gc.Clear(rect);
    
    
    
    rect = TRect( x, y, x+10, y+10 );
    gc.SetBrushStyle( CGraphicsContext::ESolidBrush );
    gc.SetBrushColor(KRgbBlack);
    gc.DrawEllipse( TRect( rect ) );
    
    
    _LIT(KCoord,"x: %d y: %d");
    TBuf<16> coords;
	coords.Format( KCoord, x, y );
	
    gc.UseFont( CEikonEnv::Static()->NormalFont() );
    gc.DrawText(coords, TPoint(80,15));
    
    
    }


