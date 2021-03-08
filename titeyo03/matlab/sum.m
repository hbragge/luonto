vidobj = videoinput('winvideo', 1);

luku1=0;
luku2=0;
luku3=0;
luku4=0;
luku5=0;
luku6=0;
luku7=0;
luku8=0;
luku9=0;
luku10=0;
yhteensa=0;
kierros=0;
aika=0;
aika2=0;
aika3=0;
aika4=0;
lippu=0;
skipit=0;
vasen=0;
oikea=0;
lippu1=0;
tulostavaara = 0;

while 1

snapshot1 = getsnapshot(vidobj);


I = rgb2gray(snapshot1);   
J = dct2(I);            % 2-ulotteinen diskreetti kosinimuunnos




v = size(J);
korkeus = v(:,1);
leveys = v(:,2);
summa = 0;
i_alk = 1;
j_alk = 1;

for i = i_alk:leveys
for j = j_alk:korkeus
    if abs(J(j,i)) >=15
        summa = summa+1;            % lasketaan kertoimien määrä, joiden arvo ylittää arvon 10
    end
end
end

if kierros == 0     % ensimmäiset 10 kierrosta
    luku1=summa;
    luku2=summa;
    luku3=summa;
    luku4=summa;
    luku5=summa;
    luku6=summa;
    luku7=summa;
    luku8=summa;
    luku9=summa;
    luku10=summa;
    luku11=summa;
end

% lasketaan 11 viimeisen arvon painotettu keskiarvo (viimeistä 
% painotetaan eniten), jotta keskiarvo lähestyy nopeammin nykytilaa
ka = .5*luku11+.6*luku10+.7*luku9+.8*luku8+.9*luku7+1*luku6+1.1*luku5+1.2*luku4+1.3*luku3+1.4*luku2+1.5*luku1;
keskiarvo = ka/11;


if kierros - aika2 > 3
    if lippu1==1
        skipit=0;
    end
end



if kierros-aika2 > 2       % radikaalista muutoksesta kulunut aika

    if skipit < 2       % huomioidaan pysyvä radikaali muutos huomioimalla summa

        if summa < 0.55*keskiarvo   % eliminoidaan radikaalien muutosten vaikutus palautteeseen
           aika2 = kierros;
           lippu = 1;       % älä anna palautetta tältä kierrokselta
           lippu1 = 1;
           skipit = skipit+1;

        else        % normaalien säätömuutosten tapahtuessa
            luku11=luku10;
            luku10=luku9;
            luku9=luku8;
            luku8=luku7;
            luku7=luku6;
            luku6=luku5;
            luku5=luku4;
            luku4=luku3;
            luku3=luku2;
            luku2=luku1;
            luku1=summa;
            lippu1=0;
        end
    else    % pysyvän radikaalin muutoksen tapahtuessa
            luku10=summa;
            luku9=summa;
            luku8=summa;
            luku7=summa;
            luku6=summa;
            luku5=summa;
            luku4=summa;
            luku3=summa;
            luku2=summa;
            luku1=summa;
            skipit = 0;
            lippu1=0;
            tulostavaara=1;
    end
end

if tulostavaara == 1   

            title('Väärä suunta!');
            vasen = 0;
            oikea = 0;   
            aika4 = kierros;
            tulostavaara = 0;
            %figure(2),imagesc(snapshot1);
            %figure(3);imshow(imfft(rgb2gray(snapshot1)),  [0  10000 ]);
%              aika = kierros;
end 


% lasketaan 11 viimeisen arvon painotettu keskiarvo (viimeisintä
% painotetaan eniten), jotta keskiarvo lähestyy nopeammin nykytilaa
ka = .5*luku11+.6*luku10+.7*luku9+.8*luku8+.9*luku7+1*luku6+1.1*luku5+1.2*luku4+1.3*luku3+1.4*luku2+1.5*luku1;
keskiarvo = ka/11;
summa
keskiarvo

%subplot(111),imshow(log(abs(J)),[]), colormap(jet);
subplot(111),imshow(snapshot1);
%if kierros-aika > 1
%if kierros-aika3 > 8
 %   oikea = 0;
%end

%if kierros-aika4 > 8
 %   vasen = 0;
%end


if luku1 > 1.05*keskiarvo % säätö tarkempaan suuntaan (huomioidaan kohina)
    if lippu==0 
        if oikea <= 0

            title('Oikea suunta!');
            oikea = 0;
            vasen = 0;
            aika3 = kierros;
            %figure(2),imagesc(snapshot1);
%               aika = kierros;
        end    
    end
end

if luku1 < 0.95*keskiarvo % säätö sumeampaan suuntaan (huomioidaan kohina)
    if lippu==0
        if vasen <= 0   

            title('Väärä suunta!');
            vasen = 0
            oikea = 0;   
            aika4 = kierros;
            %figure(2),imagesc(snapshot1);
            %figure(3);imshow(imfft(rgb2gray(snapshot1)),  [0  10000 ]);
%              aika = kierros;
        end 
    end
end
%end
lippu=0;
kierros=kierros+1;

oikea = oikea-1;
vasen = vasen-1;
end

delete(vidobj)
clear vidobj

