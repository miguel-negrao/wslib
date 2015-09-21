// wslib 2011
// quick way of monitoring all incoming osc messages
// exclude can be an array of Symbols with extra messages to exclude (i.e. not post)


OSCMonitorWSLIB {
	
	classvar <>exclude;
	
	*value { |time = 0, addr, port, msg = ([])|
		if( port.size != 0 ) { msg = port };
		if( ([ '/status.reply', '/localhostOutLevels', '/localhostInLevels' ] 
				++ exclude.asCollection ).includes( msg[0] ).not ) {
			[ time.asSMPTEString, addr, msg ].postln;
		};
	}
	
	*valueArray { arg args; ^this.value(*args) }
}

+ Main {
	monitorOSC { |bool = true, exclude|
		if( bool == true ) {
			OSCMonitorWSLIB.exclude = exclude;
			recvOSCfunc = recvOSCfunc.removeFunc( OSCMonitorWSLIB ).addFunc( OSCMonitorWSLIB );
		} {
			recvOSCfunc = recvOSCfunc.removeFunc( OSCMonitorWSLIB );
		};
	}
}
