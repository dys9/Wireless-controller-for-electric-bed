#include wiringPi.h
#include stdio.h
#include string.h
#include unistd.h
#include systypes.h
#include syssocket.h
#include netinetin.h
#include arpainet.h
#include softPwm.h
#include stdlib.h
#include string.h
#include pthread.h
#define PIN1 1
#define PIN2 0
#define PORT 2000
int set1 = 25;
int set2 = 14;
int run = 0;
int on_off = 0;
pthread_mutex_t MUTEX = PTHREAD_MUTEX_INITIALIZER;
void set90(int pin)
{
	pthread_mutex_lock(&MUTEX);
	softPwmCreate( pin,0, 340);
	printf(nset bed 90n);
	while( set1 15)
	{
		softPwmWrite(pin , set1);  -  15P 90 degree
		delay(120); 100ms during keep softPwmWrite state
		set1--;
		printf([state1]  %d n, set1);
	}
	softPwmCreate( pin,0, 0);
	pthread_mutex_unlock(&MUTEX);
}
void set0(int pin)
{
	pthread_mutex_lock(&MUTEX);
	softPwmCreate( pin,0, 340);
	printf(nset bed 0n);
	while( set1  25)
	{
		softPwmWrite(pin , set1);  -  15P 90 degree
		delay(120); 100ms during keep softPwmWrite state
		set1++;
		printf([state1]  %d n, set1);
	}
	softPwmCreate( pin,0, 0);
	pthread_mutex_unlock(&MUTEX);
}
void setup(int pin)
{
	softPwmCreate( pin,0, 340);
	printf(nset bed upn);
	if ( set1 15)
	{
		set1--;
		softPwmWrite(pin,set1);
		printf([state1]  %d n, set1);
		delay(120);
	}
	softPwmCreate( pin,0, 0);
}
void setdown(int pin)
{
	softPwmCreate( pin,0, 340);
	printf(nset bed downn);
	if ( set1 = 25)
	{
		set1++;
		softPwmWrite(pin,set1);
		printf([state1]  %d n, set1);
		delay(120);
	}
	softPwmCreate( pin,0, 0);
}
void tabledup(int pin)
{
	softPwmCreate( pin,0, 340);
	printf(nset talbe 90n);
	while( set2 25)
	{
		softPwmWrite(pin , set2);  -  15P 90 degree
		delay(120); 100ms during keep softPwmWrite state
		set2++;
		printf([state2]  %d n, set2);
	}
	softPwmCreate( pin,0, 0);
}
void tabledown(int pin)
{
	softPwmCreate( pin,0, 340);
	printf(nset table 0n);
	while( set2 14)
	{
		softPwmWrite(pin , set2);  -  15P 90 degree
		delay(120); 100ms during keep softPwmWrite state
		set2--;
		printf([state2]  %d n, set2);
	}
	softPwmCreate( pin,0, 0);
}
typedef struct Ultra
{
		int t_trig ;
		int t_echo ;
		int t_start_time, t_end_time ;
		float t_distance;
}Ultra;
void dis_check(void  arg)
{
	double array[15] = {0};
	Ultra t_ult = (Ultra )arg;
	t_ult-t_trig = 23 ;
	t_ult-t_echo = 24 ;
	int count = 0;
	if (wiringPiSetup() == -1) exit(1) ;
	pinMode(t_ult-t_trig, OUTPUT) ;
	pinMode(t_ult-t_echo , INPUT) ;
	if ( set1 != 25  set2 != 14) if not 0 degree
	{
			delay(500);
			printf(n[Check the distance]...n);
			while ( count  15)
			{
				if (run == 0 ) { printf([thread_exit]n);pthread_exit(NULL); }
				digitalWrite(t_ult-t_trig , LOW);
				delay(500);
				digitalWrite(t_ult-t_trig , HIGH);
				delayMicroseconds(10);
				digitalWrite(t_ult-t_trig , LOW);
				while (digitalRead(t_ult-t_echo) == 0);
				t_ult-t_start_time = micros();
				while (digitalRead(t_ult-t_echo) == 1) ;
				t_ult-t_end_time = micros();
				t_ult-t_distance = (t_ult-t_end_time - t_ult-t_start_time)  29.  2. ;
				printf([distance]  %.2f cmn, t_ult-t_distance) ;
				array[count] = t_ult-t_distance;
 
				count++;
			}
			int i = 0;
			for (i = 0 ; i  15; i++)
			{
					if (array[i]  8.0)
					{
					    printf([Person Check] = set bed & table 0!!!n);
					    break;
					}
					else
					{
						if ( i ==14)
						{
							printf([Nothing] = set bed & table 0!!!n);
							delay(1000);
							 set0(PIN1);
							 delay(100);
							 tabledown(PIN2);
							 break;
						}
					}
			}
			count = 0;
			pthread_mutex_lock(&MUTEX);
			run = 0 ; 
			pthread_mutex_unlock(&MUTEX);
			printf([thread_exactly_exit]n);
			pthread_exit(NULL);
	}
}
int main()
{
	Ultra ult;
	pthread_t thd;
	int status;
    if(wiringPiSetup() == -1)
        return 1;
    softPwmCreate(PIN1 ,0, 340);
    softPwmCreate(PIN2 ,0, 200);
 
   softPwmWrite(PIN1, 25);  -  25P 0 degree
   delay(600); 
 
   softPwmWrite(PIN2, 15);  -  25P 0 degree
   delay(600); 
 
    int sock;
    struct sockaddr_in addr, client_addr;
    char recv_buffer[1024];
    int recv_len;
    int addr_len;
    
    int input;
    if((sock = socket(AF_INET, SOCK_DGRAM, 0)) 0){
        perror(socket );
        return 1;
    }
 
    memset(&addr, 0x00, sizeof(addr));
    addr.sin_family = AF_INET;
    addr.sin_addr.s_addr = htonl(INADDR_ANY);
    addr.sin_port = htons(PORT);
 
    if(bind(sock, (struct sockaddr )&addr, sizeof(addr))  0){
        perror(bind );
        return 1;
    }
    
    printf(connected...n[waiting for INPUT]n);
    addr_len = sizeof(client_addr);
    
    for (;;)
    {
		if((recv_len = recvfrom(sock, recv_buffer, 1024, 0, (struct sockaddr )&client_addr, &addr_len))  0)
		{
			perror(recvfrom );
			return 1;
		}
		recv_buffer[recv_len] = '0';
		printf(n[ip  %s]n, inet_ntoa(client_addr.sin_addr));
		printf([Received PassWord  %s]n, recv_buffer);
		input = atoi(recv_buffer);
		if ( input != 101 )
		{
			printf([!!!WRONG PASSWORD!!!]n);
			sendto(sock, recv_buffer, strlen(recv_buffer), 0, (struct sockaddr )&client_addr, sizeof(client_addr));
		}
		else if ( input == 101 ) 
		{
			printf([!!!LOG_IN_COMPLETE!!!]n);
			recv_buffer[0] = '1';recv_buffer[1] = '0';recv_buffer[2] = '1';
			sendto(sock, recv_buffer, strlen(recv_buffer), 0, (struct sockaddr )&client_addr, sizeof(client_addr));
			break;
		}
	}
    
    for (;;)
    {
		if((recv_len = recvfrom(sock, recv_buffer, 1024, 0, (struct sockaddr )&client_addr, &addr_len))  0)
		{
			perror(recvfrom );
			return 1;
		}
			recv_buffer[recv_len] = '0';
			printf(n[ip  %s]n, inet_ntoa(client_addr.sin_addr));
			printf([Received INPUT  %s]n, recv_buffer);
			scanf(%d, &input);
			pthread_mutex_lock(&MUTEX);
			if (run == 1 ) {run = 0;}
			pthread_mutex_unlock(&MUTEX);
			input = atoi(recv_buffer);
			switch ( input )
			{
				case 90 
				set90(PIN1);
				break;
				case 0 
				set0(PIN1);
				break;
				case 1 
				setup(PIN1);
				break;
				case -1 
				setdown(PIN1);
				break;
				case 5 
				tabledup(PIN2);
				break;
				case -5 
				tabledown(PIN2);
				break;
				case 1024 
				on_off = 1;
				break;
				case -1024 
				on_off = 0;
				break;
		 }
		 delay(300);
		if ( on_off == 1 && run == 0  )
		{
			if ( pthread_create(&thd, NULL, dis_check,  (void )&ult) 0 ) 
			{
				exit(0);
			}
			pthread_mutex_lock(&MUTEX);
			run = 1;
			pthread_mutex_unlock(&MUTEX);
		}
	}
	pthread_join(thd, NULL);
	close(sock);
    return 0;
}