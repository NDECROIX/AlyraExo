pragma solidity ^0.5.1;

/**
* @title ERC721
*/
contract ERC721 {
    
    event Transfer(address indexed _from, address indexed _to, uint256 _tokenId);
    event Approval(address indexed _owner, address indexed _approved, uint256 _tokenId);
    
    function balanceOf(address _owner) public view returns (uint256 _balance);
    function ownerOf(uint256 _tokenId) public view returns (address _owner);
    function transferFrom(address _to, uint256 _tokenId) public;
    function approve(address _to, uint256 _tokenId) public;
    function takeOwnership(uint256 _tokenId) public;
 
}

/**
* @title ObjetsMagiques
*/   
contract RaceHorses {
    
    struct Horse {
        address payable owner;
        string name;
        string lastName;
        uint id;
        uint win;
        uint loose;
    }
    
    Horse[] horses;
    
    uint horsesMax;
    
    mapping (address => uint) ownerHorseCount;
    
    mapping (uint => address) horseOwner;
    
    address payable tresorier;
    
    constructor () public {
        tresorier = msg.sender;
        horsesMax = 2000000;
    }
    
    event horseCreate(address _owner, string _name, string _lastname, uint _id, uint _num);
    
    /**
     * @dev Creation d'un cheval de course
     * @param _name prénom du cheval 
     * @param _lastName nom du cheval
     * @return uint de la position du cheval 
     */
    function createHorse(string memory _name, string memory _lastName) public payable returns (uint) {
        require(horses.length < horsesMax, "Tous les chevaux ont été produits");
        require(msg.value >= 0.1 ether, " Cout de production 0.1 ether");
        uint _id = ((uint) (blockhash(block.number-1))) % 200000000;
        require(!horseIdExist(_id), "Id produit déjà existant");
        
        tresorier.transfer(msg.value);
        
        uint position = horses.push(Horse({ owner : msg.sender,
                                            name : _name,
                                            lastName : _lastName,
                                            id : _id,
                                            win : 0,
                                            loose : 0 }));
                            
        ownerHorseCount[msg.sender]++;
        horseOwner[position] = msg.sender;
        
        
        return position;
    }
    
    /**
     * @dev vérifie l'existance d'un id
     * @param _id id du cheval
     * @return vrais si exist
     */
    function horseIdExist(uint _id) private view returns (bool){
        uint nbrHorses = horses.length;
        for (uint i = 0; i < nbrHorses; i++){
            if (horses[i].id == _id) return true;
        }
    }
    
}

/**
* @title Hippodrome
*/  
contract Hippodrome is RaceHorses {
    
    struct Race {
        address promoter;
        uint time;
        State state;
        uint bank;
        uint firstHorse;
        address[] gamblers;
        uint[] horsesParticipates;
        mapping(uint => uint) horseStats;
        mapping(uint => uint) horseOdds;
        mapping(address => uint) playerBetOn;
        mapping(address => uint) playerShoots;
        mapping(address => bool) winners;
        
    }
    
    enum State {
        OPEN,
        RUN,
        LOAD,
        END
    }
    
    Race[] races;
    
    uint timeRace;
    
    constructor() public { timeRace = 50 ; }
    
    event Create(uint race, address promoter);
    event Participate(uint race, uint horse);
    event Launch(uint race);
    event LeaderHorse(uint _race, uint _horse);
    event HorseMoove(uint _race, uint _horse, uint _steps);
    
    modifier raceExist(uint _race) {
        require(races.length > _race, "Course inexistante");
        _;
    }
    
    modifier horseExist(uint _horse) {
        require(horses.length > _horse, "Cheval inexistant");
        _;
    }
    
    modifier raceState(uint _race, State _state) {
        require(races[_race].state == _state, "L'etat de la course ne permet pas l'accès à cette function");
        _;
    }
    
    /**
     * @dev Création d'une course de chevaux
     */
    function createRace() public payable {
        require(msg.value >= 1.01 ether, "Caution de 1 ether + 0.01 de coût = 1.01");
        
        uint indiceRace = races.push(Race({promoter : msg.sender,
                                time : block.number,
                                bank : msg.value - 1 ether,
                                state : State.OPEN,
                                horsesParticipates : new uint[](0),
                                gamblers : new address[](0),
                                firstHorse : 0 }));
        emit Create(indiceRace, msg.sender);
    }
    
    /**
     * @dev permet à un propriétaire de faire participer un cheval
     * @param _horse le cheval qu'il veut faire courir
     * @param _race la course choisi
     */
    function runner(uint _horse, uint _race) public payable raceExist(_race)
                                                            raceState(_race, State.OPEN) 
                                                            horseExist(_horse) {
                                                                
        require(msg.value >= 0.01 ether, "Mettre son cheval en course coûte 0.01 ether");
        require(horses[_horse].owner == msg.sender, "Vous n'êtes pas le propriétaire du cheval");
        require(!alreadyRunner(msg.sender, _race), "Vous participez déjà à la course");
        
        races[_race].horsesParticipates.push(_horse);
        races[_race].bank += msg.value;
        horses[_horse].loose++;
        
        emit Participate(_race, _horse);
        
    }
    
    /**
     * @dev vérifie si le propriétaire participe déjà
     * @param _owner proprio
     * @param _race la course 
     * @return bool vrais si participe déjà
     */
    function alreadyRunner(address _owner, uint _race) private view returns (bool){
        
        uint nbrParticipants = races[_race].horsesParticipates.length;
        for (uint i = 0; i < nbrParticipants ; i++ ){
            if  (horseOwner[races[_race].horsesParticipates[i]] == _owner) return true;
        }
    }
    
    /**
     * @dev inscription à une course pour parier 
     * @param _race course à laquelle je veux participer
     * @param _horse Cheval sur le quelle je parie
     */
    function gambler(uint _race, uint _horse) public payable    horseExist(_horse) 
                                                                raceExist(_race) 
                                                                raceState(_race, State.OPEN) {
                                                                
        require(runnerExist(_race, _horse), "Le cheval n'est pas inscript à la course pour le moment");
        require(msg.value >= 0.01 ether, "0.01 ether la place");
        
        races[_race].bank += msg.value;
        races[_race].gamblers.push(msg.sender);
        races[_race].horseOdds[_horse]++;
        races[_race].playerBetOn[msg.sender] = _horse;
        
    }
    
    /**
     * @dev vérifie l'existance d'un cheval dans une course
     * @param _race la course 
     * @param _horse le cheval
     * @return bool vrais si le cheval participe à la course
     */
    function runnerExist(uint _race, uint _horse) private view returns (bool){
        uint nbrParticipants = races[_race].horsesParticipates.length;
        for (uint i = 0; i < nbrParticipants ; i++ ){
            if  (races[_race].horsesParticipates[i] == _horse) return true;
        }
    }
    
    /**
     * @dev Lancer la course
     * @param _race course à Lancer
     */
    function raceLaunch(uint _race) public  raceExist(_race) 
                                            raceState(_race, State.OPEN) {
                                                
        require(races[_race].promoter == msg.sender, "Vous n'êtes pas l'organisateur de cette course");
        require(races[_race].horsesParticipates.length >= 2, "il n'y a pas assez de participants");
        
        races[_race].state = State.RUN;
        races[_race].time = block.number + timeRace;
        emit Launch(_race);
        
    }
    
    /**
     * @dev charge pour faire avancer le cheval
     * @param _race course dans la quelle on shouaite utiliser la charge
     */
    function load(uint _race) public payable    raceExist(_race) 
                                                raceState(_race, State.RUN) {
        require(races[_race].time > block.number, "La course est terminé");                                            
        require(msg.value >= 0.01 ether, "0.01 ether la charge");
        require(gamblerExist(_race, msg.sender), "Vous n'êtes pas inscrit à la course");
        
        races[_race].bank += msg.value;
        races[_race].playerShoots[msg.sender] = block.number + 1;
        
    }
    
    /**
     * @dev utiliser la charge sur un cheval pour le faire avancer
     * @param _race course dans la quelle on shouaite utiliser la charge
     */
    function shoot(uint _race) public   raceExist(_race)
                                        raceState(_race, State.RUN) {
                                            
        require(races[_race].time > block.number, "La course est terminé");
        require(gamblerExist(_race, msg.sender), "Vous n'êtes pas inscrit à la course");
        require(races[_race].playerShoots[msg.sender] > 0, "Vous n'avez pas de charge");
        require(races[_race].playerShoots[msg.sender] < block.number, "Veuillez attendre le prochain block");
        
        uint _horse = races[_race].playerBetOn[msg.sender];
        
        races[_race].playerShoots[msg.sender] = 0;
        uint power =  (uint(blockhash(races[_race].playerShoots[msg.sender])) + _horse) % 10;
        races[_race].horseStats[_horse] += power;
        
        emit HorseMoove( _race, _horse, power);
        
        if (races[_race].horseStats[_horse] > races[_race].horseStats[races[_race].firstHorse]){
            races[_race].firstHorse = _horse;
            emit LeaderHorse(_race, _horse);
        }
        
    }
    
    /**
     * @dev vérifie l'inscritpion d'un joueur dans une course
     * @param _race Course ou vérifier
     * @param _gambler adresse du joueur
     */
    function gamblerExist(uint _race, address _gambler) private view raceExist(_race) returns(bool) {
        uint nbrParticipants = races[_race].gamblers.length;
        for (uint i = 0; i < nbrParticipants ; i++ ){
            if  (races[_race].gamblers[i] == _gambler) return true;
        }
    }
    
    /**
     * @dev Terminé la course pour calculer les recettes
     * @param _race course Terminé
     */
    function closeTheRace(uint _race) public    raceExist(_race)
                                                raceState(_race, State.RUN) {
                                                    
        require(races[_race].promoter == msg.sender, "Vous n'êtes pas l'organisateur de cette course");
        require(races[_race].time < block.number, "La course n'est pas terminé");
        
        races[_race].state = State.LOAD;
        
        horses[races[_race].firstHorse].win++;
        horses[races[_race].firstHorse].loose--;
        
        uint gain = 10 * races[_race].bank / 100;
        
        msg.sender.transfer(gain + 1 ether);
        tresorier.transfer(gain);
        horses[races[_race].firstHorse].owner.transfer(gain);
        
        races[_race].bank = races[_race].bank - (gain * 3);
        uint reste = races[_race].bank;
        races[_race].bank = races[_race].bank / races[_race].horseOdds[races[_race].firstHorse];
        
        reste = reste - (races[_race].bank * races[_race].horseOdds[races[_race].firstHorse]);
        if (reste > 0) horses[races[_race].firstHorse].owner.transfer(reste);
        
        races[_race].state = State.END;
        
    }
    
    /**
     * @dev Récupération des gains des joueurs
     * @param _race course sur laquelle prendre ses gains
     */
    function jackpot(uint _race) public raceExist(_race)
                                        raceState(_race, State.END) {
                                            
        require(gamblerExist(_race, msg.sender), "Vous n'êtes pas inscrit à la course");
        require(races[_race].playerBetOn[msg.sender] == races[_race].firstHorse, "Vous n'avez pas misé sur le bon cheval");
        require(!races[_race].winners[msg.sender], "Vous avez déjà pris vos gains");
        require(races[_race].bank > 0, "Recettes nul");
        
        races[_race].winners[msg.sender] = true;
        msg.sender.transfer(races[_race].bank);
        
    }

}

contract RaceHorseOwnership is Hippodrome, ERC721 {
    
    event Transfer(address indexed _from, address indexed _to, uint256 _tokenId);
    event Approval(address indexed _owner, address indexed _approved, uint256 _tokenId);
        
    mapping(uint256 => address) tokenApprovals;
    
    function verifApprouve(uint _tokenId) public view returns(bool){
        if (tokenApprovals[_tokenId] == msg.sender) return true;
    }
    
    function balanceOf(address _owner) public view returns (uint256 _balance){
        return ownerHorseCount[_owner];
    }
     
    function ownerOf(uint256 _tokenId) public view returns (address _owner){
        require(horses.length > _tokenId);
        return horseOwner[_tokenId];
    }
     
    function exists(uint256 _tokenId) public view returns (bool exist){
        if (horses.length > _tokenId) return true;
    }
    
    /**
	* @dev transférer un cheval
	* @param _from  adresse du propriétaire
	* @param _to  adresse de destination
	* @param _tokenId num du cheval
	*/
    function _transfer(address _from, address _to, uint256 _tokenId) private {
        horseOwner[_tokenId] = _to;
        ownerHorseCount[_from]--;
        ownerHorseCount[_to]++;
        emit Transfer( _from, _to, _tokenId);
    }
    
    /**
	* @dev transférer un cheval
	* @param _to  adresse de destination
	* @param _tokenId num du cheval
	*/
    function transferFrom( address _to, uint256 _tokenId) public {
        require( _tokenId < 200 );
        require(horseOwner[_tokenId] == msg.sender);
        _transfer(msg.sender, _to, _tokenId);

    }
    
    /**
	* @dev Approuver le changement de possession d'un cheval
	* @param _to  adresse du prochain propriétaire
	* @param _tokenId num du cheval
	*/
    function approve(address _to, uint256 _tokenId) public  {
        require( _tokenId < 200 );
        require(ownerOf(_tokenId) == msg.sender);
        tokenApprovals[_tokenId] = _to;
        emit Approval(msg.sender, _to, _tokenId);
    }
    
    /**
	* @dev Prendre possession d'un cheval approuvé
	* @param _tokenId num du cheval
	*/
    function takeOwnership(uint256 _tokenId) public {
        require( _tokenId < 200 );
        require(tokenApprovals[_tokenId] == msg.sender);
        address owner = ownerOf(_tokenId);
        _transfer(owner, msg.sender, _tokenId);
    }
    
}








