	public final LevelEndHandler levelEndHandler;
		this.timeline = GameTimeline.getInstance().createTimeline();
		this.levelEndHandler = new LevelEndHandler(root);
