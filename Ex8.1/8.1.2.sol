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
    
    modifier onlyMontant() {
        require(etat == EtatCanal.VIDE);
        uint reste = montant - (equilibreA + equilibreB);
        if (msg.value > reste){
            msg.sender.transfer(msg.value - reste);
        }
        _;
    }
    
    function () external payable onlyMontant {
        require(msg.sender == partieA || msg.sender == partieB);
        
        if (msg.sender == partieA) equilibreA += msg.value;
        else if (msg.sender == partieB) equilibreB += msg.value;
        
        if(equilibreA + equilibreB >= montant) etat = EtatCanal.ACTIF;
        
    }
    

}





