#!/usr/bin/env python3 
# -*- coding: utf-8 -*-
import itertools

satoshis = [13000, 9000, 2000, 1500, 3500, 2800, 5000, 1500] 
octets   = [2000,  6000, 800,  700,  1200, 1000, 1300, 600 ]  
octetsMax = 6000
octetsUtulise = octetsMax
n = len(satoshis) 
K = [[0 for x in range(octetsMax+1)] for x in range(n+1)] #tableau pour retrouver les transactions

def meilleurPBR( satoshis , octets , octetsM , n):
    """
        fonction qui retourne le maximum de satoshis
        sans depasser le maximum d'octets autorisé "octetsM"
    """
    global K  
    if n == 0 or octetsM == 0 : 
        return 0
        
    if (octets[n-1] > octetsM): # si le bloc d'octets est suppérieur au max autorisé on passe au suivant 
        K[n][octetsM] = meilleurPBR( satoshis, octets ,  octetsM, n-1) 
        return meilleurPBR( satoshis, octets ,  octetsM, n-1) 
  
    else: # retourne la valeur maximum

        K[n][octetsM] = max(meilleurPBR( satoshis, octets , octetsM - octets[n-1]  , n-1) + satoshis[n-1], meilleurPBR( satoshis, octets , octetsM , n-1)) 
                   
        return K[n][octetsM] 

    
def rechercheTransaction(K, ressourcesSatoshis, W):

    lysteTransaction = []
    lysteSatoshis = []
    
    ressourcesOctets = W
    for i in range(n, 0, -1): #on par de la fin du tableau
        
        if ressourcesSatoshis <= 0:
            break
		
        if ressourcesSatoshis == K[i-1][ressourcesOctets]: #si la valeur des satoshi et la même à K[i - 1][w] on continue 
            continue
		
        else: # l'on soustrait les satoshis aux ressourcesSatoshis et les octets aux ressourcesOctets
			
            lysteTransaction.append(octets[i-1])
            lysteSatoshis.append(satoshis[i-1])
            
            ressourcesSatoshis = ressourcesSatoshis - satoshis[i - 1]
            ressourcesOctets = ressourcesOctets - octets[i - 1]
            
    print("Les transactions nécessaire pour atteindre le plus haut rendement de satoshis sont :", lysteTransaction )
    print("Pour un totale de ", K[n][W], "satoshis, par la somme des valeurs suivante : ", lysteSatoshis)     


rechercheTransaction(K, meilleurPBR( satoshis, octets , octetsMax , n), octetsMax)  
print ("On a une complexité de O(2^n)")

# source : https://fr.wikipedia.org/wiki/Probl%C3%A8me_du_sac_%C3%A0_dos
# le plus dur à trouver c'est le nom du prblm "Knapsack" :)  
# vous pouvez me demander directement pour pls de détaille
