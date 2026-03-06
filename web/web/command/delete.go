package command

import (
	"github.com/TypingHare/web.carton/v2026/web/web/share"
	"github.com/spf13/cobra"
)

func DeleteCommand(d share.WebDecorationLike) *cobra.Command {
	command := &cobra.Command{
		Use:   "delete <name>",
		Short: "Delete a web record",
		Args:  cobra.ExactArgs(1),
		RunE: func(cmd *cobra.Command, args []string) error {
			return share.DeleteWeb(d, args[0])
		},
	}

	return command
}
