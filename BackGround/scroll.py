from pygame import *

init()
size = width, height = 800, 600
screen = display.set_mode(size)

backPic=image.load("BackGround.png") #2440x210
BLACK=(0,0,0)
WHITE = (255,255,255)
RED   = (255,  0,  0)
GROUND = height

jumpSpeed = -15
gravity = 1
bottom = GROUND

X=0
Y=1
W=2
H=3
BOT=2

  #X #Y BOT
v=[0,0,bottom]
        #X   Y  W  H
p=Rect(200,190,20,20)

          #X   Y  W  H
plats=[Rect(270,170,60,20),Rect(350,150,60,20),Rect(780,150,60,20),Rect(980,130,60,20)]




def drawScene(screen,p,plats):
    
    offset=200-p[X]
    screen.blit(backPic,(offset,0))
    for plat in plats:
        plat=plat.move(offset,0)#move "offset" pixels horizontally, 0 pixels vertically
        draw.rect(screen,WHITE,plat)
    draw.rect(screen,RED,[200,p[1],p[2],p[3]])
    display.flip()
    print(offset,v,p)

def move(p):
    keys = key.get_pressed()
    #print(p[Y] + p[H],bottom,v[Y],0)
    if keys[K_SPACE] and p[Y] + p[H] == v[BOT] and v[Y] == 0:    
        v[Y] = jumpSpeed           # player must be sitting steady on a platform or the ground in order to jump
    if keys[K_LEFT] and p[X]>200:
        v[X] = -5
    elif keys[K_RIGHT] and p[X]<2240:
        v[X] = 5
    else:
        v[X] = 0
    # move p
    p[X] += v[X]
    v[Y] += gravity

def check(p,plats):
    
    for plat in plats:
        if p[X]+p[W]>plat[X] and p[X]<plat[X]+plat[W] and p[Y]+p[H]<=plat[Y] and p[Y]+p[H]+v[Y]>plat[Y]:
        
            # if player is horizontally within the platform ends, and if it is going to cross the plat (after moving):
            v[BOT] = plat[Y]
            p[Y] = v[BOT] - p[H]
            v[Y] =0
        
    p[Y] += v[Y]
    if p[Y]+p[H] >= GROUND:# if player attempts to fall below the ground
        v[BOT] = GROUND
        p[Y] = GROUND - p[H]
        v[Y] = 0

    
running = True
myClock=time.Clock()
while running:
    for evnt in event.get():               
        if evnt.type == QUIT:
            running = False

    move(p)
    check(p,plats)
    drawScene(screen,p,plats)
    myClock.tick(60)

quit()


  
