pragma solidity ^0.5.1;

contract CanalDePaiment {
    
    enum EtatCanal {
        VIDE,
        ACTIF,
        ENCOURSFERMETURE,
        FERME
    }
    
    address partieA;
    address partieB;
    uint montant;
    EtatCanal etat;
    uint blocFermeture; 
    uint dernierNonce;
    uint equilibreA;
    uint equilibreB;
    
    constructor(uint _montant, address _partieA, address _partieB) public {
        
        montant = _montant;
        partieA = _partieA;
        partieB = _partieB;
        
    }
    

}




